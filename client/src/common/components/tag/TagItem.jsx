import { XMarkIcon } from "@/static/svg";

const TagItem = ({ tag, onRemove }) => {
  return (
    <span className="inline-flex justify-center px-2 py-1.5 rounded-full text-xs cursor-pointer text-indigo-500 bg-indigo-50 hover:bg-indigo-100 transition-colors">
      {tag}
      {onRemove && (
        <XMarkIcon
          onClick={onRemove}
          className="ml-1 my-auto h-4 w-4 stroke-indigo-500"
        />
      )}
    </span>
  );
};

export default TagItem;
