import { MouseEventHandler, ReactNode } from "react";
import { toast } from "react-toastify";

import { Article } from "../types";

import { LinkArticleContent } from "./LinkArticleContent";
import { LikeButton } from "./LikeButton";
import { CopyButton } from "./CopyButton";

export interface LinkArticleProps {
  article: Article;
  onClick?: MouseEventHandler<HTMLDivElement>;
  ControlComponent?: ReactNode;
}

export const LinkArticle = ({
  article,
  onClick,
  ControlComponent,
}: LinkArticleProps) => {
  const handleCopy = () => {
    navigator.clipboard.writeText(article.url);
    toast.info("클립보드에 복사되었습니다.", {
      position: "bottom-center",
      autoClose: 1500,
    });
  };

  return (
    <article className="py-6 w-full border-b border-gray-200">
      <LinkArticleContent article={article} onClick={onClick} />
      <div className="relative flex items-center gap-2 pt-4">
        <CopyButton onClick={handleCopy} />
        {ControlComponent && ControlComponent}
      </div>
    </article>
  );
};
