import ArticleCardBookmark from "../ArticleCardBookmark";

const ArticleCard = ({ article }) => {
  return (
    <article className="flex h-20 w-full my-5 cursor-pointer hover:bg-slate-50">
      <img className="min-w-[60px] min-h-[60px] md:min-w-[80px] md:min-h-[80px]" />
      <div className="flex flex-col w-full pl-4">
        <h1>{article.title}</h1>
        <p className="text-gray-600">{article.description}</p>
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
      <ArticleCardBookmark />
    </article>
  );
};

export default ArticleCard;
