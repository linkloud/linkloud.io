import TagItem from "@/common/components/tag/TagItem";
import AnchorHoverable from "@/common/components/anchor/AnchorHoverable";
import { ArrowRightIcon } from "@/static/svg";

const TagItemContainer = ({ tagList }) => {
  return (
    <aside className="hidden md:block max-w-sm w-full py-8">
      <h1 className="sr-only">side</h1>
      <div className="w-full">
        <div className="pb-2 flex w-auto stroke-gray-600">
          <AnchorHoverable to="/tags" className="flex items-center text-sm">
            모든 태그 <ArrowRightIcon className="w-4 h-4" />
          </AnchorHoverable>
        </div>
        <nav className="flex items-center">
          <h1 className="hidden">tag list</h1>
          <ul className="flex flex-wrap gap-2.5 my-2">
            {tagList.length > 0 ? (
              tagList.map((tag) => (
                <li key={tag.id}>
                  <TagItem tag={tag} />
                </li>
              ))
            ) : (
              <span>등록된 태그가 없습니다.</span>
            )}
          </ul>
        </nav>
      </div>
    </aside>
  );
};

export default TagItemContainer;
