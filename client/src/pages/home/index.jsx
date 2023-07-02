import { useState, useEffect } from "react";
import { toast } from "react-toastify";

import useArticleSearch from "@/hooks/article/useArticleSearch";
import useArticleSearchValidation from "@/hooks/article/useArticleSearchValidation";
import useTags from "@/hooks/tag/useTags";
import useModalWithReset from "@/hooks/useModalWithReset";

import articleApi from "@/service/api/article";

import { ERROR_CODE } from "@/common/constants";

import Banner from "./components/Banner";
import ArticleItemContainer from "@/common/components/article/ArticleItemContainer";
import TagItemContainer from "@/common/components/tag/TagItemContainer";
import Search from "@/common/components/search";
import SearchValidationErrorModal from "@/common/components/search/SearchValidationErrorModal";

const HomePage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [articles, setArticles] = useState([]);
  const [articlePageInfo, setArticlePageInfo] = useState({
    page: 1,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });
  const { handleSearch } = useArticleSearch();
  const { searchValidationError, setSearchValidationError, validateSearch } =
    useArticleSearchValidation();
  const { isOpened, openModal, closeModal } = useModalWithReset(false, () =>
    setSearchValidationError("")
  );
  const { tags, fetchTagsError } = useTags({
    page: 1,
    size: 15,
    sortBy: "popularity",
  });

  useEffect(() => {
    fetchArticles(currentPage);
  }, [currentPage]);

  useEffect(() => {
    if (fetchTagsError) {
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.", {
        toastId: ERROR_CODE.SERVER_ERROR,
      });
    }
  }, [fetchTagsError]);

  useEffect(() => {
    if (searchValidationError) openModal();
  }, [searchValidationError]);

  const fetchArticles = async (page) => {
    try {
      const { data, pageInfo } = await articleApi.getList({ page });
      if (!data) return;
      setArticles((prev) => [...prev, ...data]);
      setArticlePageInfo(pageInfo);
    } catch (err) {
      // setError(err);
    } finally {
      // setLoading(false);
    }
  };

  const handleSearchSubmit = (keyword) => {
    if (!validateSearch(keyword)) return;
    handleSearch(keyword);
  };

  const handleClickNext = () => {
    if (currentPage >= articlePageInfo.totalPages) return;
    setCurrentPage(currentPage + 1);
  };

  return (
    <>
      <Banner articleCounts={articlePageInfo.totalElements || 0} />

      <section className="flex flex-col items-center px-5 md:px-0 w-full">
        <h1 className="sr-only">검색</h1>
        <div className="w-full max-w-xl translate-y-[-50%]">
          <Search onSearch={handleSearchSubmit} />
        </div>
        {searchValidationError && (
          <SearchValidationErrorModal
            isOpened={isOpened}
            onClose={closeModal}
            message={searchValidationError}
          />
        )}
      </section>

      <div className="flex w-full max-w-7xl">
        <ArticleItemContainer
          articles={articles}
          pageInfo={articlePageInfo}
          onClickNext={handleClickNext}
        />
        <TagItemContainer tags={tags} />
      </div>
    </>
  );
};

export default HomePage;
