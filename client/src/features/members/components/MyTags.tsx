import { TagItem } from "@/features/tags";
import { Tag } from "@/features/tags/types";

interface MyTagsProps {
  tags: Tag[];
}

export const MyTags = ({ tags }: MyTagsProps) => {
  return (
    <section className="py-6 scroll-none overflow-x-auto whitespace-nowrap">
      <ul className="flex gap-2">
        <li>
          <TagItem name="전체" size="md" />
        </li>
        {tags.map((tag) => (
          <li key={tag.id}>
            <TagItem name={tag.name} size="md" />
          </li>
        ))}
      </ul>
    </section>
  );
};
