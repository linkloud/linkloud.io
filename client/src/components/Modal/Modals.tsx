import { useAuthActions } from "@/stores/useAuthStore";
import useModalStore from "@/stores/useModalStore";

import { AuthModal } from "../Auth";

export const Modals = () => {
  const { isLoggedIn } = useAuthActions();
  const { modals, handleCloseModal } = useModalStore((state) => ({
    modals: state.items,
    handleCloseModal: state.closeModal,
  }));

  return (
    <>
      {isLoggedIn() ? null : (
        <AuthModal
          isOpened={modals.auth}
          onClose={() => handleCloseModal("auth")}
        />
      )}
    </>
  );
};
