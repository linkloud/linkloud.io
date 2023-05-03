import TagCard from "@/common/components/tag/TagCard";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";

import { fakeTagList } from "@/common/utils/fakedata";

import useModalStore from "@/stores/useModalStore";

const TagsPage = () => {
  // 태그 요청 모달
  const { openModal } = useModalStore();
  const handleOpenReqTagModal = () => {
    openModal("requestTag");
  };

  const orderList = [
    {
      id: 1,
      name: "인기순",
      isSelected: true,
    },
    {
      id: 2,
      name: "최신순",
      isSelected: false,
    },
    {
      id: 3,
      name: "이름순",
      isSelected: false,
    },
  ];

  return (
    <div className="max-w-4xl px-2.5">
      <h2 className="mt-7 mb-4 font-bold text-2xl">태그</h2>
      <p className="max-w-[640px]">
        태그는 링크를 분류하는 키워드입니다. <br />
        알맞은 태그를 사용하여 다른 사람들이 해당 링크가 어떤 내용을 담고 있는지
        쉽게 파악할 수 있습니다. 새로운 태그가 필요하시다면{" "}
        <a
          onClick={handleOpenReqTagModal}
          className="underline cursor-pointer hover:text-blue-400"
        >
          여기
        </a>
        를 클릭하세요.
      </p>
      {/* 태그 정렬 옵션 */}
      <ul className="flex mt-7 mb-4">
        {orderList.map((o) => (
          <li key={o.id} onClick={() => console.log("test")} className="mr-1">
            <AnchorSelectable isSelected={o.isSelected}>
              {o.name}
            </AnchorSelectable>
          </li>
        ))}
      </ul>
      <section className="flex flex-wrap gap-y-4 gap-x-6 py-3">
        <h1 className="hidden">tag card list section</h1>
        {fakeTagList.map((item) => (
          <TagCard tag={item} key={item.id} />
        ))}
      </section>
    </div>
  );
};

export default TagsPage;
