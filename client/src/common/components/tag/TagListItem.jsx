import { Link } from "react-router-dom";

import TagItem from "./TagItem";

const TagListItem = ({ tag }) => {
  return (
    <>
      <div className="flex flex-col py-4 border-b border-gray-200">
        <div>
          <Link to={`/search?tag=${tag.name}`}>
            <TagItem tag={tag.name} />
          </Link>
        </div>
        <p className="mt-3.5">링크 {tag.count ? tag.count : 0}개</p>
      </div>
    </>
  );
};

export default TagListItem;
