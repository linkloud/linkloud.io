import Modal from "@/components/common/modal";
import InputText from "@/components/common/input/InputText";
import DefaultButton from "@/components/common/button/DefaultButton";

const TagRequestModal = ({ isOpened, onCloseReqTagModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseReqTagModal}>
      <h1 className="mb-3 mx-auto text-2xl font-semibold">
        새 태그를 요청하기
      </h1>
      <InputText labelText={"태그"} />
      <p className="py-4">
        태그 업데이트 여부는 공지사항을 통해 확인하실 수 있습니다.
      </p>
      <div className="flex m-full justify-end gap-2.5 mt-2">
        <DefaultButton styleType="line" size="md" onClick={onCloseReqTagModal}>
          닫기
        </DefaultButton>
        <DefaultButton styleType="fill" size="md">
          요청
        </DefaultButton>
      </div>
    </Modal>
  );
};

export default TagRequestModal;
