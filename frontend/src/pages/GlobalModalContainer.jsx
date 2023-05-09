import AuthLoginModal from "@/common/components/auth/AuthLoginModal";
import TagRequestModal from "@/common/components/tag/TagRequestModal";
import ArticleRegConfirmModal from "@/common/components/article/ArticleRegConfirmModal";

import useModalStore from "@/stores/useModalStore";

const GlobalModalContainer = () => {
  const {
    closeModal,
    login: isLoginModalOpened,
    requestTag: isReqTagModalOpened,
    articleRegConfirm: isArticleRegConfirmModalOpened,
  } = useModalStore();

  const handleCloseLoginModal = () => closeModal("login");
  const handleCloseReqTagModal = () => closeModal("requestTag");
  const handleCloseArticleConfirmModal = () => closeModal("articleRegConfirm");

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
      <ArticleRegConfirmModal
        isOpened={isArticleRegConfirmModalOpened}
        onCloseArticleRegConfirmModal={handleCloseArticleConfirmModal}
      ></ArticleRegConfirmModal>
    </>
  );
};

export default GlobalModalContainer;
