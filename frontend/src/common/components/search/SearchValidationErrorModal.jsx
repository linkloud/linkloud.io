import Modal from "../modal";

const SearchValidationErrorModal = ({ isOpened, onClose, message }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onClose}>
      <h1 className="sr-only">검색 에러</h1>
      <span className="pt-4 px-5">{message}</span>
    </Modal>
  );
};

export default SearchValidationErrorModal;
