import useModalStore from "@/stores/useModalStore";
import useAuthStore from "@/stores/useAuthStore";

import { AuthModal } from "../Auth";

export const Modals = () => {
  const isAuth = useAuthStore((state) => state.isAuth);
  const { modals, handleCloseModal } = useModalStore((state) => ({
    modals: state.items,
    handleCloseModal: state.closeModal,
  }));

  return (
    <>
      {isAuth() ? null : (
        <AuthModal
          isOpened={modals.auth}
          onClose={() => handleCloseModal("auth")}
        />
      )}
    </>
  );
};
