import Modal from "../modal";
import InputText from "../input/InputText";
import Button from "../button";

const ArticleRegConfirmModal = ({
  isOpened,
  onCloseArticleRegConfirmModal,
}) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseArticleRegConfirmModal}>
      <h1 className="sr-only">register tag in article modal</h1>
      <p className="text-lg font-semibold">
        링크가 무엇인지 설명하는 태그를 넣으면 찾기 쉬워질거에요.
        <span className="text-gray-600 text-base"> (최대 5개)</span>
      </p>
      <InputText labelText="태그를 추가하세요" className="mt-6"></InputText>
      <div className="mt-1">인기태그</div>
      <div className="mt-8 flex justify-end">
        <Button
          onClick={onCloseArticleRegConfirmModal}
          size="md"
          styleType="lined"
          className="mr-3"
        >
          닫기
        </Button>
        <Button size="md" styleType="fill">
          등록하기
        </Button>
      </div>
    </Modal>
  );
};

export default ArticleRegConfirmModal;
