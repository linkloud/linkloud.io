import { BookmarkIcon, LinkOutIcon } from "@/static/svg";

const ArticleItem = ({ article }) => {
  return (
    <article className="pt-5 ">
      {/* wrap */}
      <div className="group flex">
        {/* left */}
        <div className="w-full cursor-pointer">
          {/* content */}
          <div className="flex flex-col w-full">
            <h1 className="md:text-lg font-medium">{article.title}</h1>
            <a
              onClick={(e) => e.preventDefault}
              href={article.link}
              className="flex items-center text-gray-600"
            >
              {article.link}
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
                article.tags.map((t) => (
                  <li
                    key={t.id}
                    className="mr-2 text-gray-400 text-xs md:text-sm"
                  >
                    #{t.name}
                  </li>
                ))}
            </ul>
          </div>
        </div>
        {/* right */}
        <div className="flex flex-col items-center px-2 md:px-3">
          <button type="button" title="북마크" className="px-2 pb-2">
            <BookmarkIcon className="w-6 h-6 stroke-gray-400 text-xs md:text-sm" />
          </button>
          <span className="text-gray-600">
            {article.counts ? article.counts : 0}
          </span>
        </div>
      </div>
      <hr />
    </article>
  );
};

export default ArticleItem;
