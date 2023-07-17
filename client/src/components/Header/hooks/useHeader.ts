import { useState, MouseEvent } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useAuthStore from "@/stores/useAuthStore";

import { ROUTE_PATH } from "@/routes/constants";

const useHeader = () => {
  const [isAuthModalVisible, setIsAuthModalVisible] = useState(false);
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const userInfo = useAuthStore((state) => state.userInfo);
  const logout = useAuthStore((state) => state.logout);
  const navigate = useNavigate();

  const handleAuthModal = (state: boolean) => {
    setIsAuthModalVisible(state);
  };

  const handleRegisterLink = (e: MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault();

    if (userInfo.role === "USER") {
      setIsAuthModalVisible(true);
      return;
    }

    navigate(ROUTE_PATH.LINK.REG);
  };

  const handleClickAvatar = () => {
    setIsActionMenuVisible(true);
  };

  const handleLeaveAvatar = () => {
    setIsActionMenuVisible(false);
  };

  const handleLogout = async () => {
    try {
      await logout();
    } catch (e) {
      toast.error("로그아웃에 실패했습니다");
    }
  };

  return {
    userInfo,
    isAuthModalVisible,
    handleAuthModal,
    isActionMenuVisible,
    handleRegisterLink,
    handleClickAvatar,
    handleLeaveAvatar,
    handleLogout,
  };
};

export default useHeader;
