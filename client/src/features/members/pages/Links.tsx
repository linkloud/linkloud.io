import { Button } from "@/components/Button";
import { SelectableChip } from "@/components/Chip";
import { Head } from "@/components/Head";
import { Spinner } from "@/components/Spinner";
import { useUser } from "@/stores/useAuthStore";

import { MyArticleSort } from "../apis";
import { MyLinkArticles } from "../components/MyLinkArticles";
import { MyTags } from "../components/MyTags";
import { useMyArticles, useMyTags } from "../hooks";

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

  const user = useUser();
  const {
    articles,
    articlesPageInfo,
    articlesLoading,
    articlesError,
    tag,
    sortBy,
    handleNextArticles,
    handlePrevArticles,
    handleChangeTag,
    handleChangeSort,
    handleReadStatus,
  } = useMyArticles(user.id);
  const { tags, tagsLoading, tagsError } = useMyTags(user.id);

  const prevButtonVisible = articlesPageInfo.page > 1;
  const nextButtonVisible = articlesPageInfo.page < articlesPageInfo.totalPages;

  return (
    <>
      <Head title="링클라우드 | 내 링크" />
      <section className="w-full max-w-7xl px-6 pb-20">
        <h1 className="sr-only">내 링크</h1>

        {!tagsLoading && <MyTags tags={tags} onClickTag={handleChangeTag} />}

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

        {!articlesLoading && (
          <MyLinkArticles
            articles={articles}
            onUpdateReadStatus={handleReadStatus}
          />
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
