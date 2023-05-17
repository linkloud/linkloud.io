import { create } from "zustand";

const useModalStore = create((set) => ({
  login: false,
  requestTag: false,
  articleRegConfirm: false,
  toggleModal: (type) => {
    set((state) => ({ ...state, [type]: !state }));
  },
  openModal: (type) => {
    set((state) => ({ ...state, [type]: true }));
  },
  closeModal: (type) => {
    set((state) => ({ ...state, [type]: false }));
  },
}));

export default useModalStore;
