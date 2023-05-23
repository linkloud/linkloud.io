import Modal from "@/common/components/modal";
import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";

const ArticleRegConfirmModal = ({ isOpened, onClose, tagList }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onClose}>
      <h1 className="sr-only">register tag in article modal</h1>
      <p className="self-start">
        링크가 무엇인지 설명하는 태그를 넣으면 찾기 쉬워질거에요.
        <span className="text-gray-600 text-sm"> (최대 5개)</span>
      </p>
      <InputText labelText="태그를 추가하세요" className="mt-4"></InputText>
      <div className="mt-2 self-start flex">
        인기태그:
        <ul className="ml-2 flex">
          {tagList &&
            tagList.map((t, index) => (
              <li key={t.id} className="mr-1 cursor-pointer">
                <span className="text-primary-600">{t.name}</span>
                {index !== tagList.length - 1 && <>,</>}
              </li>
            ))}
        </ul>
      </div>
      <div className="mt-8 flex justify-end">
        <Button onClick={onClose} size="md" styleType="lined" className="mr-3">
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
