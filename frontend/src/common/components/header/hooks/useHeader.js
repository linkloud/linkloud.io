import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";

import useModal from "@/hooks/useModal";
import useScrolledPastThreshold from "@/hooks/useScrolledPastThreshold";

import { ROLE } from "@/common/constants";

const useHeader = () => {
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const navigate = useNavigate();

  const { userInfo, initUserInfo } = useAuthStore();
  const {
    isOpened: isLoginModalOpened,
    openModal: handleOpenLoginModal,
    closeModal: handleCloseLoginModal,
  } = useModal();
  const { isScrollTop } = useScrolledPastThreshold();

  useEffect(() => {
    initUserInfo();
  }, []);

  // 링크 등록
  const handleRegisterLink = () => {
    if (!userInfo.role || userInfo.info === ROLE.GUEST) {
      setOpen("login");
      return;
    }
    navigate("/links/reg");
  };

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
    handleClickProfile,
    handleLeaveProfile,
  };
};

export default useHeader;
