import { Link } from "react-router-dom";

import { Tag } from "../types";

import { TagItem, TagItemProps } from "./TagItem";

export interface TagItemsProps extends Omit<TagItemProps, "name"> {
  tags: string[] | Tag[];
}

export const TagItems = ({ tags, size = "md" }: TagItemsProps) => {
  const handleTagClick = (event: React.MouseEvent) => {
    event.stopPropagation();
  };

  return (
    <ul className="flex gap-2">
      {tags.length > 0 &&
        tags.map((tag, index) => {
          const tagName = typeof tag === "string" ? tag : tag.name;
          return (
            <li key={`${tagName}-${index}`} onClick={handleTagClick}>
              <Link to={`/search?tags=${tagName}`}>
                <TagItem name={tagName} size={size} />
              </Link>
            </li>
          );
        })}
    </ul>
  );
};
