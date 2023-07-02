import { useEffect } from "react";
import useModal from "./useModal";

function useModalWithReset(initialValue = false, resetFunction = () => {}) {
  const { isOpened, openModal, closeModal: _close } = useModal(initialValue);

  const closeModal = () => {
    _close(); // 모달 닫기
    resetFunction(); // 모달 닫으면 reset 함수 호출
  };

  // clean up
  useEffect(() => {
    return () => resetFunction();
  }, []);

  return { isOpened, openModal, closeModal };
}

export default useModalWithReset;
