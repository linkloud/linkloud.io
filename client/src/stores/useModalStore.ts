import { create } from "zustand";

type Modal = "auth";

interface ModalState {
  items: Record<Modal, boolean>;
  openModal: (type: Modal) => void;
  closeModal: (type: Modal) => void;
}

const useModalStore = create<ModalState>()((set) => ({
  items: {
    auth: false,
  },
  openModal: (type) => {
    set((state) => ({ ...state, items: { [type]: true } }));
  },
  closeModal: (type) => {
    set((state) => ({ ...state, items: { [type]: false } }));
  },
}));

export default useModalStore;
