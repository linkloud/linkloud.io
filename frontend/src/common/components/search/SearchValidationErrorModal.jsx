import Modal from "../modal";

const SearchValidationErrorModal = ({ isOpened, onClose, errMsg }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onClose}>
      <h1 className="sr-only">검색 에러</h1>
      <span className="px-5">{errMsg}</span>
    </Modal>
  );
};

export default SearchValidationErrorModal;
