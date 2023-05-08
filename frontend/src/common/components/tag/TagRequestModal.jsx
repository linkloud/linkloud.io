import Modal from "@/common/components/modal";
import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";

const TagRequestModal = ({ isOpened, onCloseReqTagModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseReqTagModal}>
      <h1 className="mb-3 mx-auto text-2xl font-semibold">
        새 태그를 요청하기
      </h1>
      <InputText labelText={"태그 이름"} />
      <p className="py-4">
        태그 업데이트 여부는 공지사항을 통해 확인하실 수 있습니다.
      </p>
      <div className="flex m-full justify-end gap-2.5 mt-2">
        <Button styleType="lined" size="md" onClick={onCloseReqTagModal}>
          닫기
        </Button>
        <Button styleType="fill" size="md">
          요청
        </Button>
      </div>
    </Modal>
  );
};

export default TagRequestModal;
