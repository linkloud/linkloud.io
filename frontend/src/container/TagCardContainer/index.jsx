import TagCard from "@/components/tag/TagCard";

const TagCardContainer = () => {
  const fakeTagList = [
    {
      id: 1,
      name: "태그1",
      description:
        "설명이 길어질 때 설명이 길어질 때 설명이 길어질 때 설명이 길어질 때 설명이 길어질 때 설명이 길어질 때 설명이 길어질 때",
      articleCounts: 1,
    },
    {
      id: 2,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 3,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 4,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 5,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 6,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 7,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 8,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 9,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 10,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 11,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 12,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 13,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 14,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
    {
      id: 15,
      name: "태그1",
      description: "설명",
      articleCounts: 1,
    },
  ];

  return (
    <section className="flex flex-wrap gap-y-4 gap-x-6 py-3">
      <h1 className="hidden">태그 카드 리스트</h1>
      {fakeTagList.map((t) => (
        <TagCard tag={t} key={t.id} />
      ))}
    </section>
  );
};

export default TagCardContainer;
