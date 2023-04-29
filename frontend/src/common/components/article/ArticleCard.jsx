import { StarIcon } from "@/static/svg";

const ArticleCard = ({ article }) => {
  return (
    <article className="group flex h-20 w-full my-5 cursor-pointer hover:bg-slate-50">
      <img className="min-w-[60px] min-h-[60px] md:min-w-[80px] md:min-h-[80px]" />
      <div className="flex flex-col w-full pl-4">
        <h1>{article.title}</h1>
        <p className="text-gray-600 group-hover:hidden">
          {article.description}
        </p>
        <a
          onClick={(e) => e.preventDefault}
          href="https://test.com"
          className="hidden group-hover:block text-blue-500"
        >
          article link
        </a>
        {article.tags && (
          <ul className="mt-auto flex">
            {article.tags.map((t) => (
              <li key={t.name} className="text-gray-400 mr-1 text-sm">
                {t.name}
              </li>
            ))}
          </ul>
        )}
      </div>
      {/* bookmark */}
      <div className="flex flex-col items-center justify-between gap-4 px-2 md:px-3 py-2.5 border-l-2 border-gray-200">
        <StarIcon className="w-6 h-6 stroke-gray-400 text-xs md:text-sm" />
        <p>{article.counts}</p>
      </div>
    </article>
  );
};

export default ArticleCard;
