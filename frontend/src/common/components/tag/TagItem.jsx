import { Link } from "react-router-dom";

const TagItem = ({ tag }) => {
  return (
    <Link
      to={`/search?tag=${tag.name}`}
      className="inline-flex justify-center px-2 py-1.5 rounded text-xs cursor-pointer text-indigo-400 bg-indigo-50 hover:bg-indigo-100 transition-colors"
    >
      {tag.name}
    </Link>
  );
};

export default TagItem;
