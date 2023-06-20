import useArticleDetail from "@/hooks/article/useArticleDetail";

import ArticleItem from "./ArticleItem";
import ArticleNotFound from "./ArtcleNotFound";

const ArticleItemContainer = ({ articles }) => {
  const { handleArticleClick } = useArticleDetail();

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
    </section>
  );
};

export default ArticleItemContainer;
