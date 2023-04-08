import { ModalWrap, ModalContent } from "./style";
import Portal from "../potal";
import Dimmed from "../dimmed";

const Modal = ({ isOpened, onCloseModal, children }) => {
  return (
    <Portal elementId="modal-wrap">
      {isOpened && (
        <ModalWrap>
          <Dimmed onClick={onCloseModal}></Dimmed>
          <ModalContent>{children}</ModalContent>
        </ModalWrap>
      )}
    </Portal>
  );
};

export default Modal;
