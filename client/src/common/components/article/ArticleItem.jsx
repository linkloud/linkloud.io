import TagItem from "../tag/TagItem";
import { BookmarkIcon, LinkOutIcon } from "@/static/svg";

const ArticleItem = ({ article, onClickArticle }) => {
  return (
    <article onClick={() => onClickArticle(article.id)} className="pt-5">
      {/* wrap */}
      <div className="group flex">
        {/* left */}
        <div className="w-full cursor-pointer">
          {/* content */}
          <div className="flex flex-col w-full">
            <h1 className="md:text-lg font-medium">{article.title}</h1>
            <a
              onClick={(e) => e.preventDefault}
              href={article.url}
              className="flex items-center text-gray-600"
            >
              {article.url}
              <LinkOutIcon className="ml-1 w-5 h-5 group-hover:stroke-blue-600" />
            </a>
            <p className="mt-1 line-clamp-2 text-xs md:text-sm text-gray-400">
              {article.description}
            </p>
          </div>
          {/* tags */}
          <div className="py-5">
            <ul className="flex">
              {article.tags &&
                article.tags.map((tag) => (
                  <li key={tag.id} className="mr-2">
                    <TagItem tag={tag.name} />
                  </li>
                ))}
            </ul>
          </div>
        </div>
      </div>
      <hr />
    </article>
  );
};

export default ArticleItem;
