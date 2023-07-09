import { Link } from "react-router-dom";

import { Tag } from "../types";

import { TagItem } from "./TagItem";

export interface TagCardProps {
  tag: Tag;
}

export const TagCard = ({ tag }: TagCardProps) => {
  return (
    <div className="flex flex-col py-4 border-b border-gray-200">
      <div>
        <Link to={`/search?tags=${tag.name}`}>
          <TagItem name={tag.name} size="md" />
        </Link>
      </div>
      <p className="mt-4">링크 {tag.count ? tag.count : 0}개</p>
    </div>
  );
};
