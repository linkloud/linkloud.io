import { useState } from "react";

import useTagList from "@/hooks/tag/useTagList";
import useScrollToView from "@/hooks/useScrollToView";

import TagListItem from "@/common/components/tag/TagListItem";
import TagNotFound from "@/common/components/tag/TagNotFound";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";
import Button from "@/common/components/button";

import { TAG_SORT_OPTIONS } from "@/common/constants";

const TAG_SORT_LIST = [
  {
    id: 1,
    name: "인기순",
    value: TAG_SORT_OPTIONS.POPULARITY,
  },
  {
    id: 2,
    name: "최신순",
    value: TAG_SORT_OPTIONS.LATEST,
  },
  {
    id: 3,
    name: "이름순",
    value: TAG_SORT_OPTIONS.NAME,
  },
];

const TagListPage = () => {
  const [tagSortOption, setTagSortOption] = useState(
    TAG_SORT_OPTIONS.POPULARITY
  );

  const {
    tagList,
    tagPageInfo,
    handlePrevTagList,
    handleNextTagList,
    handleChangeSortOption,
  } = useTagList({
    page: 1,
    size: 10,
    sortBy: tagSortOption,
  });
  const { ref: sortRef } = useScrollToView([tagPageInfo.page]);

  const handleChangeTagOption = (tagOption) => {
    setTagSortOption(tagOption);
    handleChangeSortOption(tagOption);
  };

  const prevButtonVisible = tagPageInfo.page > 1;
  const nextButtonVisible = tagPageInfo.page < tagPageInfo.totalPages;

  return (
    <div className="max-w-7xl px-6">
      <h2 className="mt-7 mb-4 font-bold text-2xl">태그</h2>
      <p ref={sortRef} className="max-w-[640px]">
        태그는 링크를 분류하는 키워드입니다. <br />
        알맞은 태그를 사용하여 다른 사람들이 해당 링크가 어떤 내용을 담고 있는지
        쉽게 파악할 수 있습니다.
      </p>

      {/* 태그 정렬 옵션 */}
      <ul className="flex mt-7 mb-4">
        {TAG_SORT_LIST.map((order) => (
          <li key={order.id}>
            <AnchorSelectable
              onClick={() => handleChangeTagOption(order.value)}
              isSelected={tagSortOption === order.value}
            >
              {order.name}
            </AnchorSelectable>
          </li>
        ))}
      </ul>

      <section className="flex flex-col gap-y-4 gap-x-6 py-3">
        <h1 className="hidden">태그 목록</h1>
        {tagList.length > 0 ? (
          <>
            <ul>
              {tagList.map((tag) => (
                <li key={tag.id}>
                  <TagListItem tag={tag} />
                </li>
              ))}
            </ul>
            <div className="flex mt-5 mb-24">
              {prevButtonVisible && (
                <Button size="sm" styleType="lined" onClick={handlePrevTagList}>
                  이전
                </Button>
              )}
              {nextButtonVisible && (
                <Button
                  size="sm"
                  styleType="lined"
                  onClick={handleNextTagList}
                  className={"ml-auto"}
                >
                  다음
                </Button>
              )}
            </div>
          </>
        ) : (
          <TagNotFound />
        )}
      </section>
    </div>
  );
};

export default TagListPage;
