import { useSearchParams } from "react-router-dom";

import useArticleSearch from "@/hooks/article/useArticleSearch";
import useTagList from "@/hooks/tag/useTagList";

import Search from "@/common/components/search";
import SearchValidationErrorModal from "@/common/components/search/SearchValidationErrorModal";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";
import ArticleItem from "@/common/components/article/ArticleItem";
import TagItemContainer from "@/common/components/tag/TagItemContainer";

import { fakeArticleList } from "@/common/utils/fakedata";

const SearchPage = () => {
  const orderList = [
    {
      id: 1,
      name: "최신순",
      isSelected: true,
    },
    {
      id: 2,
      name: "인기순",
      isSelected: false,
    },
  ];

  const [searchParams] = useSearchParams();
  // TODO: keyword -> api DOC
  const searchKeyword = searchParams.get("keyword");
  const tagList = searchParams.getAll("tag");

  const {
    searchValidationErrMsg,
    isSearchValidationErrorModalOpened,
    handleSearch,
    handleCloseSearchValidationModal,
  } = useArticleSearch();

  //TODO: 예외처리
  const { tagList: popularityTagList, error: getTagsError } = useTagList({
    page: 1,
    size: 15,
    sortBy: "popularity",
  });

  return (
    <div className="flex flex-col py-10 max-w-7xl w-full">
      <section className="mx-auto max-w-xl w-full">
        <h1 className="sr-only">search section</h1>
        <Search onSearch={handleSearch} />
        <SearchValidationErrorModal
          isOpened={isSearchValidationErrorModalOpened}
          errMsg={searchValidationErrMsg}
          onClose={handleCloseSearchValidationModal}
        />
      </section>
      <div className="px-6 py-5">
        <h2 className="text-xl">
          {searchKeyword ? (
            <>
              <strong>'{searchKeyword}'</strong> 검색 결과
            </>
          ) : (
            <>태그로 검색 결과</>
          )}
        </h2>
        {tagList.length > 0 && (
          <>
            {searchKeyword && (
              <p className="mt-1">다음 태그와 함께 검색되었습니다.</p>
            )}
            <ul className="flex">
              {tagList.map((tag, index) => (
                <li key={index} className="mr-1 text-gray-400">
                  #{tag}
                  {index !== tagList.length - 1 && <>,</>}
                </li>
              ))}
            </ul>
          </>
        )}
      </div>
      <div className="flex w-full max-w-7xl">
        <section className="w-full p-6">
          <h1 className="hidden">link article list section</h1>
          <div className="hidden md:block w-full mb-4">
            <nav>
              <h1 className="hidden">link article order option</h1>
              <ul className="flex py-3">
                {orderList.map((o) => (
                  <li key={o.id}>
                    <AnchorSelectable isSelected={o.isSelected}>
                      {o.name}
                    </AnchorSelectable>
                  </li>
                ))}
              </ul>
            </nav>
          </div>
          {fakeArticleList?.map((a) => (
            <ArticleItem article={a} key={a.id}></ArticleItem>
          ))}
        </section>
        <TagItemContainer tagList={popularityTagList} />
      </div>
    </div>
  );
};

export default SearchPage;
