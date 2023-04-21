import {
  Container,
  TagDescriptionTitle,
  TagDescription,
  TagRequestAction,
  TagCardList,
} from "./style";
import TagRequestModal from "@/components/tag/TagRequestModal";
import TagOrderList from "@/components/tag/TagOrderList";
import TagCard from "@/components/tag/TagCard";

import {
  useRequestTagModalState,
  useModalActions,
} from "@/stores/useModalStore";

const TagsPage = () => {
  // 태그 요청 모달
  const isReqTagModalOpened = useRequestTagModalState();
  const { setOpen, setClose } = useModalActions();

  const handleOpenReqTagModal = () => {
    setOpen("requestTag");
  };

  const handleCloseReqTagModal = () => {
    setClose("requestTag");
  };
  // 태그 요청 모달 끝

  const fakeTagList = [
    {
      id: 1,
      name: "태그1",
      description: "설명",
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
    <Container>
      <TagDescriptionTitle>태그</TagDescriptionTitle>
      <TagDescription>
        태그는 링크를 분류하는 키워드입니다. <br />
        알맞은 태그를 사용하여 다른 사람들이 해당 링크가 어떤 내용을 담고 있는지
        쉽게 파악할 수 있습니다. 새로운 태그가 필요하시다면{" "}
        <TagRequestAction onClick={handleOpenReqTagModal}>
          여기
        </TagRequestAction>
        를 클릭하세요.
      </TagDescription>
      <TagRequestModal
        isOpened={isReqTagModalOpened}
        onCloseReqTagModal={handleCloseReqTagModal}
      ></TagRequestModal>
      <TagOrderList />
      <TagCardList>
        <h1>태그 카드 리스트</h1>
        {fakeTagList.map((t) => (
          <TagCard tag={t} key={t.id} />
        ))}
      </TagCardList>
    </Container>
  );
};

export default TagsPage;
