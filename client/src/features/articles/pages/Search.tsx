import { useArticleSearch, useArticle } from "../hooks";

import { TagItems } from "@/features/tags";
import { Button } from "@/components/Button";
import { Head } from "@/components/Head";
import { LinkArticle } from "../components/LinkArticle";

const Search = () => {
  const {
    searchKeyword,
    searchTags,
    searchedArticles,
    searchedArticlesPageInfo,
    handleSearchPrev,
    handleSearchNext,
  } = useArticleSearch({});
  const { articleError, handleClickArticle } = useArticle();

  const generateDescription = (
    searchKeyword: string | null,
    searchTags: string[] | null
  ) => {
    const searchTagsString = searchTags?.join(", ");

    let description = "";
    if (searchKeyword) description = `${searchKeyword} 검색 결과`;
    else description = "태그로 검색 결과";

    if (searchTagsString) description += `. 관련 태그: ${searchTagsString}`;

    return description;
  };

  const prevButtonVisible = searchedArticlesPageInfo.page > 1;
  const nextButtonVisible =
    searchedArticlesPageInfo.page < searchedArticlesPageInfo.totalPages;

  const description = generateDescription(searchKeyword, searchTags);

  return (
    <>
      <Head title="링클라우드 | 검색 결과" description={description} />
      <section className="pb-20 w-full max-w-7xl px-6">
        <h1 className="pt-6 text-2xl md:text-3xl font-medium">
          {searchKeyword ? (
            <>
              <strong>'{searchKeyword}'</strong> 검색 결과
            </>
          ) : (
            <>태그로 검색 결과</>
          )}
        </h1>
        {searchTags?.length > 0 && (
          <>
            {searchKeyword && (
              <p className="pt-1">다음 태그와 함께 검색되었습니다.</p>
            )}
            <div className="pt-2">
              <TagItems tags={searchTags} size="md" />
            </div>
          </>
        )}

        {searchedArticles?.length > 0 && (
          <ul>
            {searchedArticles.map((article) => (
              <li key={article.id}>
                <LinkArticle
                  article={article}
                  onClick={() => handleClickArticle(article.id)}
                />
              </li>
            ))}
          </ul>
        )}
        {searchedArticles?.length === 0 && (
          <div className="flex flex-col justify-center items-center h-96 w-full">
            <p className="text-2xl font-semibold">검색 결과가 없습니다.</p>
            <p className="pt-2 text-lg text-neutral-600">
              검색어를 다시 입력해주세요.
            </p>
          </div>
        )}

        <div className="flex justify-between pt-8">
          {prevButtonVisible && (
            <Button
              name="이전 링크 목록 검색 버튼"
              styleName="outline-neutral"
              size="lg"
              onClick={handleSearchPrev}
            >
              이전
            </Button>
          )}
          {nextButtonVisible && (
            <Button
              name="다음 링크 목록 검색 버튼"
              styleName="outline-neutral"
              size="lg"
              onClick={handleSearchNext}
              className="ml-auto"
            >
              다음
            </Button>
          )}
        </div>
      </section>
    </>
  );
};

export default Search;
