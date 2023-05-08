import TagItem from "@/common/components/tag/TagItem";
import AnchorHoverable from "@/common/components/anchor/AnchorHoverable";
import { ArrowRightIcon } from "@/static/svg";

import { fakeHashTags } from "@/common/utils/fakedata";

const Aside = () => {
  return (
    <aside className="max-w-xs w-full p-6">
      <h1 className="hidden">side</h1>
      <div className="my-5 w-full">
        <div className="pb-2 flex w-auto stroke-gray-600">
          <AnchorHoverable to="/tags" className="flex items-center text-sm">
            모든 태그 <ArrowRightIcon className="w-4 h-4" />
          </AnchorHoverable>
        </div>
        <nav className="flex items-center justify-center">
          <h1 className="hidden">tag list</h1>
          <ul className="flex flex-wrap gap-2.5 my-2">
            {fakeHashTags &&
              fakeHashTags.map((tag) => (
                <li key={tag}>
                  <TagItem>{tag}</TagItem>
                </li>
              ))}
          </ul>
        </nav>
      </div>
    </aside>
  );
};

export default Aside;
