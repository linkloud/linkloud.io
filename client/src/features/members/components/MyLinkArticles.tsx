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
  if (articles.length === 0)
    return (
      <div className="flex justify-center items-center h-44 w-full">
        <span>링크가 없습니다.</span>
      </div>
    );

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
