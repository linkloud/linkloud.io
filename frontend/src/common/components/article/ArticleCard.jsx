import { StarIcon } from "@/static/svg";

const ArticleCard = ({ article }) => {
  return (
    <article className="group flex md:h-20 w-full my-5 cursor-pointer hover:bg-slate-50">
      <img className="min-w-[60px] min-h-[60px] md:min-w-[80px] md:min-h-[80px]" />
      <div className="flex flex-col w-full pl-4">
        <h1 className="text-sm md:text-base">{article.title}</h1>
        <p className="text-xs md:text-sm text-gray-600 group-hover:hidden">
          {article.description}
        </p>
        <a
          onClick={(e) => e.preventDefault}
          href="https://test.com"
          className="hidden text-sm md:text-base text-blue-500 group-hover:block"
        >
          article link
        </a>
        {article.tags && (
          <ul className="mt-auto flex">
            {article.tags.map((t) => (
              <li
                key={t.name}
                className="mr-1 text-gray-400 text-xs md:text-sm"
              >
                {t.name}
              </li>
            ))}
          </ul>
        )}
      </div>
      {/* bookmark */}
      <div className="flex flex-col items-center md:justify-between md:gap-4 px-2 md:px-3 md:py-2.5 text-sm">
        <StarIcon className="w-6 h-6 stroke-gray-400 text-xs md:text-sm" />
        <p className="text-gray-600">{article.counts}</p>
      </div>
    </article>
  );
};

export default ArticleCard;
