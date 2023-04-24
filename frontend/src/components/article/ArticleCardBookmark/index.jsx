import { StarIcon } from "@/static/svg";

const ArticleCardBookmark = ({ isBookmarked = false, counts = 0 }) => {
  return (
    <div className="flex flex-col items-center justify-between gap-4 px-2 md:px-3 my-2.5 border-l-2 border-gray-200">
      <StarIcon className="w-6 h-6 stroke-gray-600 text-xs md:text-sm" />
      <p>{counts}</p>
    </div>
  );
};

export default ArticleCardBookmark;
