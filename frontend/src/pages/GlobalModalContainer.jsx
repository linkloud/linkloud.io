import AuthLoginModal from "@/common/components/auth/AuthLoginModal";
import TagRequestModal from "@/common/components/tag/TagRequestModal";

import useModalStore from "@/stores/useModalStore";

const GlobalModalContainer = () => {
  const {
    login: isLoginModalOpened,
    requestTag: isReqTagModalOpened,
    closeModal,
  } = useModalStore();

  const handleCloseLoginModal = () => closeModal("login");
  const handleCloseReqTagModal = () => closeModal("requestTag");

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
