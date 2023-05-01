import AuthLoginModal from "@/common/components/auth/AuthLoginModal";
import TagRequestModal from "@/common/components/tag/TagRequestModal";

import useModalStore, { useModalActions } from "@/stores/useModalStore";

const GlobalModalContainer = () => {
  const { setClose } = useModalActions();

  // login modal
  const isLoginModalOpened = useModalStore((state) => state.login);
  const handleCloseLoginModal = () => setClose("login");

  // request tag modal
  const isReqTagModalOpened = useModalStore((state) => state.requestTag);
  const handleCloseReqTagModal = () => setClose("requestTag");

  return (
    <>
      <AuthLoginModal
        isOpened={isLoginModalOpened}
        onCloseLoginModal={handleCloseLoginModal}
      />
      <TagRequestModal
        isOpened={isReqTagModalOpened}
        onCloseReqTagModal={handleCloseReqTagModal}
      ></TagRequestModal>
    </>
  );
};

export default GlobalModalContainer;
