import { useMyArticles, useMyTags } from "../hooks";
import useAuthStore from "@/stores/useAuthStore";

import { MyArticleSort } from "../apis";

import { Head } from "@/components/Head";
import { SelectableChip } from "@/components/Chip";
import { Spinner } from "@/components/Spinner";
import { Button } from "@/components/Button";
import { MyTags } from "../components/MyTags";
import { MyLinkArticles } from "../components/MyLinkArticles";

interface SortOption {
  id: number;
  name: string;
  value: MyArticleSort;
}

const Links = () => {
  const sortOptions: SortOption[] = [
    {
      id: 1,
      name: "최신순",
      value: "latest",
    },
    {
      id: 2,
      name: "이름순",
      value: "title",
    },
    {
      id: 3,
      name: "읽는중",
      value: "reading",
    },
    {
      id: 4,
      name: "읽음",
      value: "read",
    },
  ];

  const userInfo = useAuthStore((state) => state.userInfo);
  const {
    articles,
    articlesPageInfo,
    articlesLoading,
    articlesError,
    tag,
    sortBy,
    handleNextArticles,
    handlePrevArticles,
    handleChangeSort,
    handleReadStatus,
  } = useMyArticles(userInfo.id);
  const { tags, tagsLoading, tagsError } = useMyTags(userInfo.id);

  const prevButtonVisible = articlesPageInfo.page > 1;
  const nextButtonVisible = articlesPageInfo.page < articlesPageInfo.totalPages;

  return (
    <>
      <Head title="링클라우드 | 내 링크" />
      <section className="w-full max-w-7xl px-6 pb-20">
        <h1 className="sr-only">내 링크</h1>

        {!tagsLoading && tags.length === 0 && <div className="py-8"></div>}
        {/* {!tagsLoading && tags.length > 0 && <MyTags tags={tags} />} */}

        <ul className="flex gap-2 pb-3">
          {sortOptions.map((option) => (
            <li key={option.id}>
              <a>
                <SelectableChip
                  isSelected={sortBy === option.value}
                  onClick={() => handleChangeSort(option.value)}
                >
                  {option.name}
                </SelectableChip>
              </a>
            </li>
          ))}
        </ul>

        {articlesLoading && (
          <div className="flex justify-center items-center w-full">
            <Spinner className="mt-10" />
          </div>
        )}

        {!articlesLoading && articles.length > 0 && (
          <MyLinkArticles
            articles={articles}
            onUpdateReadStatus={handleReadStatus}
          />
        )}

        {!articlesLoading && articles.length === 0 && (
          <div className="flex justify-center items-center h-44 w-full">
            <span>링크가 없습니다.</span>
          </div>
        )}

        <div className="flex justify-between pt-8">
          {prevButtonVisible && (
            <Button
              name="이전 링크 목록 조회 버튼"
              styleName="outline-neutral"
              size="lg"
              onClick={handlePrevArticles}
            >
              이전
            </Button>
          )}
          {nextButtonVisible && (
            <Button
              name="다음 링크 목록 조회 버튼"
              styleName="outline-neutral"
              size="lg"
              onClick={handleNextArticles}
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

export default Links;