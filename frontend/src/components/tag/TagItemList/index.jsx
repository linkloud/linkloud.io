import Tag from "../TagItem";
import Anchor from "@/components/common/anchor/AnchorDefault";

import { ArrowRightIcon } from "@/static/svg";

const TagItemList = () => {
  const fakeTags = [
    "#무료",
    "#스프링",
    "#프론트엔드",
    "#디자인",
    "#아티클",
    "#리액트",
  ];

  return (
    <div className="my-5 w-full">
      <div className="pb-2 flex w-auto stroke-gray-600">
        <Anchor href="/tags" className="flex items-center text-sm">
          모든 태그 <ArrowRightIcon className="w-4 h-4" />
        </Anchor>
      </div>
      <nav className="flex items-center justify-center">
        <h1 className="hidden">tag list</h1>
        <ul className="flex flex-wrap gap-2.5 my-2">
          {fakeTags &&
            fakeTags.map((tag) => (
              <li key={tag}>
                <Tag>{tag}</Tag>
              </li>
            ))}
        </ul>
      </nav>
    </div>
  );
};

export default TagItemList;
