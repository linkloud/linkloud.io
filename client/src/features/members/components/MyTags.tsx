import { useState } from "react";

import { TagItem } from "@/features/tags";
import { Tag } from "@/features/tags/types";

interface MyTagsProps {
  tags: Tag[];
  onClickTag?: (tag: string) => void;
}

export const MyTags = ({ tags, onClickTag }: MyTagsProps) => {
  const [selectedTag, setSelectedTag] = useState("전체");

  const handleClickTag = (tag: string) => {
    setSelectedTag(tag);
    if (onClickTag) onClickTag(tag);
  };

  if (tags.length === 0) return <div className="py-8"></div>;

  return (
    <section className="py-6 scroll-none overflow-x-auto whitespace-nowrap">
      <ul className="flex gap-2">
        <li>
          <TagItem
            name="전체"
            size="md"
            isSelected={selectedTag === "전체"}
            onClick={() => handleClickTag("전체")}
          />
        </li>
        {tags.map((tag) => (
          <li key={tag.id}>
            <TagItem
              name={tag.name}
              size="md"
              isSelected={tag.name === selectedTag}
              onClick={() => handleClickTag(tag.name)}
            />
          </li>
        ))}
      </ul>
    </section>
  );
};
