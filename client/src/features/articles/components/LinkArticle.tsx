import { MouseEventHandler } from "react";
import { toast } from "react-toastify";

import { Article } from "../types";

import { LinkArticleContent } from "./LinkArticleContent";
import { LikeButton } from "./LikeButton";
import { CopyButton } from "./CopyButton";

export interface LinkArticleProps {
  article: Article;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

export const LinkArticle = ({ article, onClick }: LinkArticleProps) => {
  const hanleCopy = () => {
    navigator.clipboard.writeText(article.url);
    toast.info("클립보드에 복사되었습니다.", {
      position: "bottom-center",
      autoClose: 1500,
    });
  };

  return (
    <article className="py-6 w-full border-b border-gray-200">
      <LinkArticleContent article={article} onClick={onClick} />
      <div className="pt-4">
        <CopyButton onClick={hanleCopy} />
        {/* <LikeButton /> */}
      </div>
    </article>
  );
};
