import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";

import useArticleSearch from "@/hooks/article/useArticleSearch";
import useArticleDetail from "@/hooks/article/useArticleDetail";
import useTagList from "@/hooks/tag/useTagList";
import { getArticleList } from "@/service/api";

import Banner from "./components/Banner";
import TagItemContainer from "@/common/components/tag/TagItemContainer";
import Search from "@/common/components/search";
import SearchValidationErrorModal from "@/common/components/search/SearchValidationErrorModal";
import ArticleItem from "@/common/components/article/ArticleItem";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";
import ArticleNotFound from "@/common/components/article/ArtcleNotFound";

const HomePage = () => {
  const [articleList, setArticleList] = useState([]);
  const [articlePageInfo, setArticlePageInfo] = useState({
    page: 1,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });
  const [searchParams] = useSearchParams();

  const {
    searchValidationErrMsg,
    isSearchValidationErrorModalOpened,
    handleSearch,
    handleCloseSearchValidationModal,
  } = useArticleSearch();
  const { handleArticleClick } = useArticleDetail();

  //TODO: 예외처리
  const { tagList, error: getTagListError } = useTagList({
    page: 1,
    size: 15,
    sortBy: "popularity",
  });

  const sortBy = searchParams.get("sortBy") || "createdAt";

  const sortList = [
    {
      id: 1,
      displayName: "최신순",
      name: "createdAt",
      isSelected: sortBy === "createdAt",
    },
    {
      id: 2,
      displayName: "인기순",
      name: "popularity",
      isSelected: sortBy === "popularity",
    },
    {
      id: 3,
      displayName: "이주의 링크",
      name: "weekly",
      isSelected: sortBy === "weekly",
    },
    {
      id: 4,
      displayName: "이달의 링크",
      name: "monthly",
      isSelected: sortBy === "monthly",
    },
  ];

  useEffect(() => {
    fetchArticleList(1);
  }, []);

  const fetchArticleList = async (page) => {
    try {
      const { data, pageInfo } = await getArticleList({ page });
      setArticleList(data);
      setArticlePageInfo(pageInfo);
    } catch (err) {
      // setError(err);
    } finally {
      // setLoading(false);
    }
  };

  return (
    <>
      <Banner articleCounts={articlePageInfo.totalElements} />
      <section className="px-5 md:px-0 w-full max-w-xl translate-y-[-50%]">
        <h1 className="sr-only">search section</h1>
        <Search onSearch={handleSearch} />
        <SearchValidationErrorModal
          isOpened={isSearchValidationErrorModalOpened}
          errMsg={searchValidationErrMsg}
          onClose={handleCloseSearchValidationModal}
        />
      </section>
      <div className="flex w-full max-w-7xl">
        <section className="w-full p-6">
          <h1 className="hidden">link article list section</h1>
          <div className="hidden md:block w-full mb-4">
            <nav>
              <h1 className="hidden">link article order option</h1>
              <ul className="flex py-3">
                {sortList.map((s) => (
                  <li key={s.id}>
                    <AnchorSelectable
                      isSelected={s.isSelected}
                      to={`?sortBy=${s.name}`}
                    >
                      {s.displayName}
                    </AnchorSelectable>
                  </li>
                ))}
              </ul>
            </nav>
          </div>
          {articleList.length > 0 ? (
            articleList.map((article) => (
              <ArticleItem
                onClickArticle={handleArticleClick}
                article={article}
                key={article.id}
              ></ArticleItem>
            ))
          ) : (
            <ArticleNotFound />
          )}
        </section>
        <TagItemContainer tagList={tagList} />
      </div>
    </>
  );
};

export default HomePage;
