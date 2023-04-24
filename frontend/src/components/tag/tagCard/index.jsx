import TagItem from "../TagItem";

const TagCard = ({ tag }) => {
  return (
    <div className="flex flex-col items-start w-[180px] h-[180px] p-4 rounded border border-gray-400">
      <TagItem>{tag.name}</TagItem>
      <p className="mt-1 overflow-hidden line-clamp-4 text-sm text-gray-600">
        {tag.description}
      </p>
      <p className="text-xs mt-auto text-gray-600">
        링크 {tag.articleCounts}개
      </p>
    </div>
  );
};

export default TagCard;
