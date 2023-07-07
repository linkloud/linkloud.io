import { MouseEventHandler } from "react";

import { Article } from "../types";

import { Link } from "@/components/Link";
import { TagItems } from "@/features/tags/components/TagItems";

export interface LinkArticleContentProps {
  article: Article;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

export const LinkArticleContent = ({
  article,
  onClick,
}: LinkArticleContentProps) => {
  return (
    <div className="group cursor-pointer" onClick={onClick}>
      <h1 className="text-lg md:text-xl font-medium line-clamp-2 break-all">
        {article.title}
      </h1>

      <div>
        <Link
          to={article.url}
          className="group-hover:underline text-blue-600 text-sm md:text-base line-clamp-1 break-all"
          onClick={(e) => e.preventDefault()}
        >
          {article.url}
        </Link>
      </div>

      <p className="pt-1 text-sm md:text-base text-gray-400 line-clamp-2 break-all">
        {article.description}
      </p>

      <div className="pt-6 overflow-x-auto whitespace-nowrap">
        <TagItems tags={article.tags} size="sm" />
      </div>
    </div>
  );
};
