import { useState } from "react";
import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";
import useModalStore from "@/stores/useModalStore";
import useScrolledPastThreshold from "@/hooks/useScrolledPastThreshold";

import { ROLE } from "@/common/constants";

const useHeader = () => {
  const navigate = useNavigate();

  const { userInfo } = useAuthStore();
  const { openModal } = useModalStore();
  const { isScrollTop } = useScrolledPastThreshold();
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const handleOpenLoginModal = () => openModal("login");

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
    handleOpenLoginModal,
    handleRegisterLink,
    handleClickProfile,
    handleLeaveProfile,
  };
};

export default useHeader;
