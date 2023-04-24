import TagRequestModal from "@/components/tag/TagRequestModal";
import TagOrderList from "@/components/tag/TagOrderList";
import TagCardContainer from "@/container/TagCardContainer";

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
      <TagRequestModal
        isOpened={isReqTagModalOpened}
        onCloseReqTagModal={handleCloseReqTagModal}
      ></TagRequestModal>
      <TagOrderList />
      <TagCardContainer />
    </div>
  );
};

export default TagsPage;
