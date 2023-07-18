import { useEffect, useRef } from "react";

import { useTags } from "../hooks";

import { TagSort } from "../types";

import { TagCard } from "../components/TagCard";
import { Button } from "@/components/Button";
import { SelectableChip } from "@/components/Chip";
import { Head } from "@/components/Head";

interface TagSortOption {
  id: number;
  name: string;
  value: TagSort;
}

const tagSortOptions: TagSortOption[] = [
  {
    id: 1,
    name: "인기순",
    value: "popularity",
  },
  {
    id: 2,
    name: "최신순",
    value: "latest",
  },
  {
    id: 3,
    name: "이름순",
    value: "name",
  },
];

const Tags = () => {
  const isFristMounted = useRef(true);

  const {
    tags,
    tagsPageInfo,
    tagsSortOption,
    tagsLoading,
    tagsError,
    handlePrevTags,
    handleNextTags,
    handleTagsSortOption,
  } = useTags({
    page: 1,
    size: 10,
    sortBy: "popularity",
  });

  useEffect(() => {
    if (!isFristMounted.current) {
      isFristMounted.current = false;
      return;
    }
    window.scrollTo({ top: 150 });
  }, [tagsPageInfo.page]);

  const prevButtonVisible = tagsPageInfo.page > 1;
  const nextButtonVisible = tagsPageInfo.page < tagsPageInfo.totalPages;

  return (
    <>
      <Head
        title="링클라우드 | 태그"
        description="태그는 링크를 분류하는 키워드입니다. 알맞은 태그를 사용하여 다른 사람들이 해당 링크가 어떤 내용을 담고 있는지 쉽게 파악할 수 있습니다."
      />
      <section className="pb-20 w-full max-w-7xl px-6">
        <h1 className="pt-6 text-3xl font-medium">태그</h1>
        <p className="py-4 text-lg">
          태그는 링크를 분류하는 키워드입니다. <br />
          알맞은 태그를 사용하여 다른 사람들이 해당 링크가 어떤 내용을 담고
          있는지 쉽게 파악할 수 있습니다.
        </p>

        <ul className="flex gap-2 pt-8 pb-4">
          {tagSortOptions.map((option) => (
            <li key={option.id}>
              <a>
                <SelectableChip
                  isSelected={tagsSortOption === option.value}
                  onClick={() => handleTagsSortOption(option.value)}
                >
                  {option.name}
                </SelectableChip>
              </a>
            </li>
          ))}
        </ul>

        <div>
          {tags?.length > 0 &&
            tags.map((tag) => <TagCard key={tag.id} tag={tag}></TagCard>)}
        </div>

        <div className="flex justify-between pt-8">
          {prevButtonVisible && (
            <Button
              name="이전 태그 목록 조회 버튼"
              styleName="outline-neutral"
              size="lg"
              onClick={handlePrevTags}
            >
              이전
            </Button>
          )}
          {nextButtonVisible && (
            <Button
              name="다음 태그 목록 조회 버튼"
              styleName="outline-neutral"
              size="lg"
              onClick={handleNextTags}
              className="ml-auto"
            >
              다음
            </Button>
          )}
        </div>
      </section>
    </>
  );
};

export default Tags;
