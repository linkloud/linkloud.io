import { useSearchParams } from "react-router-dom";

import useArticleSearch from "@/hooks/article/useArticleSearch";
import useTags from "@/hooks/tag/useTags";
import useModal from "@/hooks/useModal";

import Banner from "./components/Banner";
import TagItemContainer from "@/common/components/tag/TagItemContainer";
import Search from "@/common/components/search";
import SearchValidationErrorModal from "@/common/components/search/SearchValidationErrorModal";
import ArticleItem from "@/common/components/article/ArticleItem";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";

import { fakeArticleList } from "@/common/utils/fakedata";

const HomePage = () => {
  const [searchParams] = useSearchParams();

  const {
    isOpened: isSearchValidationErrorModalOpened,
    openModal: openSearchValidationModal,
    closeModal: closeSearchValidationModal,
  } = useModal();

  const { searchValidationErrMsg, handleSearch } = useArticleSearch(
    openSearchValidationModal
  );

  //TODO: 예외처리
  const { tags, error: getTagsError } = useTags({
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

  return (
    <>
      <Banner />
      <section className="px-5 md:px-0 w-full max-w-xl translate-y-[-50%]">
        <h1 className="sr-only">search section</h1>
        <Search onSearch={handleSearch} />
        <SearchValidationErrorModal
          isOpened={isSearchValidationErrorModalOpened}
          errMsg={searchValidationErrMsg}
          onClose={closeSearchValidationModal}
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
          {fakeArticleList.map((a) => (
            <ArticleItem article={a} key={a.id}></ArticleItem>
          ))}
        </section>
        <TagItemContainer tags={tags} />
      </div>
    </>
  );
};

export default HomePage;
