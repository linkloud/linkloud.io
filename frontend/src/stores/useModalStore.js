import { create } from "zustand";

const useModalStore = create((set) => ({
  login: false, // 로그인 모달
  actions: {
    changeState: (type) => {
      set((state) => ({ ...state, [type]: !state }));
    },
    setOpen: (type) => {
      set((state) => ({ ...state, [type]: true }));
    },
    setClose: (type) => {
      set((state) => ({ ...state, [type]: false }));
    },
  },
}));

export const useModalActions = () => useModalStore((state) => state.actions);

export const useLoginModalState = () => useModalStore((state) => state.login);

export default useModalStore;
