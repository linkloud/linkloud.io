import TagItem from "./TagItem";

const TagListItem = ({ tag }) => {
  return (
    <>
      <div className="flex flex-col py-4 border-b border-gray-200">
        <div>
          <TagItem tag={tag} />
        </div>
        <p className="mt-3.5">링크 {tag.count}개</p>
      </div>
    </>
  );
};

export default TagListItem;
