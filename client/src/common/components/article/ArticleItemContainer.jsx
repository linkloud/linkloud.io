import useArticleDetail from "@/hooks/article/useArticleDetail";

import ArticleItem from "./ArticleItem";
import ArticleNotFound from "./ArtcleNotFound";
import Button from "@/common/components/button";

const ArticleItemContainer = ({ articles, pageInfo, onClickNext }) => {
  const { handleArticleClick } = useArticleDetail();

  const nextButtonVisible = pageInfo.page < pageInfo.totalPages;

  return (
    <section className="w-full p-6">
      <h1 className="hidden">링크 목록</h1>
      {articles?.length > 0 ? (
        articles.map((article) => (
          <ArticleItem
            onClickArticle={handleArticleClick}
            article={article}
            key={article.id}
          ></ArticleItem>
        ))
      ) : (
        <ArticleNotFound />
      )}
      <div className="flex justify-center mt-5 mb-24">
        {nextButtonVisible && (
          <Button size="sm" styleType="lined" onClick={onClickNext}>
            더보기
          </Button>
        )}
      </div>
    </section>
  );
};

export default ArticleItemContainer;
