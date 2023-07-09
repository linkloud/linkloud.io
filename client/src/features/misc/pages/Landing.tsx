import { useEffect, useRef } from "react";
import { toast } from "react-toastify";

import { useTags } from "@/features/tags";
import { useArticles } from "@/features/articles";
import { useArticle } from "@/features/articles";

import { Counter } from "@/components/Counter";
import { Link } from "@/components/Link";
import { Button } from "@/components/Button";
import { TagItems } from "@/features/tags/components/TagItems";
import { LinkArticle } from "@/features/articles";
import { ChevronRightIcon } from "@/assets/svg";

const Landing = () => {
  const isFristMounted = useRef(true);

  const { tags, tagsLoading, tagsError } = useTags({
    page: 1,
    size: 15,
    sortBy: "popularity",
  });
  const {
    articles,
    articlesPageInfo,
    articlesLoading,
    articlesError,
    handlePrevArticles,
    handleNextArticles,
  } = useArticles({ page: 1 });
  const { articleError, handleClickArticle } = useArticle();

  useEffect(() => {
    if (!isFristMounted.current) {
      isFristMounted.current = false;
      return;
    }

    window.scrollTo({ top: 150 });
  }, [articlesPageInfo.page]);

  useEffect(() => {
    if (articlesError || articleError)
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.");
  }, [articlesError, articleError]);

  const prevButtonVisible = articlesPageInfo.page > 1;
  const nextButtonVisible = articlesPageInfo.page < articlesPageInfo.totalPages;

  return (
    <>
      <section className="flex flex-col justify-center items-center h-60 md:h-80 w-full bg-primary-medium">
        <h1 className="px-5 text-center text-xl md:text-3xl text-neutral-50 font-medium">
          생산성을 높일 수 있는 유용한 도구와 자료를 제공하는 링크를 찾아보세요.
        </h1>
        <p className="my-3 text-md md:text-lg text-neutral-50">
          지금까지
          <Counter
            end={articlesPageInfo.totalElements}
            className="text-md md:text-lg text-neutral-50"
          />
          개의 링크가 모였습니다
        </p>
      </section>

      <section className="w-full max-w-7xl px-6">
        <div className="pt-8 flex justify-between">
          <h1 className="text-2xl font-semibold">인기 태그</h1>
          <div className="flex justify-end md:justify-start">
            <Link
              to="tags"
              className="group flex items-center hover:text-blue-500 hover:underline"
            >
              전체 태그
              <ChevronRightIcon className="h-5 w-5 stroke-gray-800 group-hover:stroke-blue-500" />
            </Link>
          </div>
        </div>

        <div className="pt-4 pb-6 overflow-x-auto whitespace-nowrap">
          <TagItems tags={tags} size="md" />
        </div>
      </section>

      <section className="w-full max-w-7xl pt-8 pb-20 px-6">
        <h1 className="sr-only">링크 목록</h1>
        <ul></ul>

        {articlesLoading && (
          <div className="flex justify-center items-center h-full max-h-96"></div>
        )}

        {!articlesLoading && articles?.length > 0 && (
          <ul>
            {articles.map((article) => (
              <li key={article.id}>
                <LinkArticle
                  article={article}
                  onClick={() => handleClickArticle(article.id)}
                />
              </li>
            ))}
          </ul>
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

export default Landing;
