import { Article, ReadStatus } from "@/features/articles/types";

import { MyLinkArticle } from "./MyLinkArticle";

interface MyLinkArticlesProps {
  articles: Article[];
  onUpdateReadStatus: (id: number, newStatus: ReadStatus) => void;
}

export const MyLinkArticles = ({
  articles,
  onUpdateReadStatus,
}: MyLinkArticlesProps) => {
  return (
    <ul>
      {articles.map((article) => (
        <li key={article.id}>
          <MyLinkArticle
            article={article}
            onUpdateReadStatus={onUpdateReadStatus}
          />
        </li>
      ))}
    </ul>
  );
};
