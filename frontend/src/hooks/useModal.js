import { useState, useCallback } from "react";

function useModal(initialValue = false) {
  const [isOpened, setIsOpened] = useState(initialValue);

  const toggleModal = () => {
    setIsOpened((v) => !v);
  };

  const openModal = () => {
    setIsOpened(true);
  };

  const closeModal = () => {
    setIsOpened(false);
  };

  return {
    isOpened,
    toggleModal,
    openModal,
    closeModal,
  };
}

export default useModal;
