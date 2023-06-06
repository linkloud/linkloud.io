import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";

import useModal from "@/hooks/useModal";
import useScrolledPastThreshold from "@/hooks/useScrolledPastThreshold";

import { ROLE } from "@/common/constants";

const useHeader = () => {
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const navigate = useNavigate();
  const userInfo = useAuthStore((state) => state.userInfo);
  const {
    isOpened: isLoginModalOpened,
    openModal: handleOpenLoginModal,
    closeModal: handleCloseLoginModal,
  } = useModal();
  const { isScrollTop } = useScrolledPastThreshold();

  /** 헤더 링크 등록 버튼 */
  const handleRegisterLink = () => {
    if (!userInfo.role || userInfo.info === ROLE.USER) {
      handleOpenLoginModal();
      return;
    }
    navigate("/links/reg");
  };

  /** 헤더 공지사항 버튼 */
  const handleClickNotice = () => {
    window.open(
      "https://github.com/linkloud/linkloud.io/wiki/%F0%9F%93%A3-Notice",
      "_blank",
      "noopener,noreferrer"
    );
  };

  /** 헤더 프로필 버튼 */
  const handleClickProfile = () => {
    setIsActionMenuVisible((prev) => !prev);
  };

  const handleLeaveProfile = () => {
    setIsActionMenuVisible(false);
  };

  return {
    userInfo,
    isScrollTop,
    isActionMenuVisible,
    isLoginModalOpened,
    handleOpenLoginModal,
    handleCloseLoginModal,
    handleRegisterLink,
    handleClickNotice,
    handleClickProfile,
    handleLeaveProfile,
  };
};

export default useHeader;
