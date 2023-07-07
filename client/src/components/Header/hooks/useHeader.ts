import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";

const useHeader = () => {
  const [isAuthModalVisible, setIsAuthModalVisible] = useState(false);
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const userInfo = useAuthStore((state) => state.userInfo);
  const logout = useAuthStore((state) => state.logout);
  const navigate = useNavigate();

  const handleAuthModal = (state: boolean) => {
    setIsAuthModalVisible(state);
  };

  const handleRegisterLink = () => {
    if (!userInfo.role || userInfo.role === "USER") setIsAuthModalVisible(true);
    navigate("/links/reg");
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
    } catch (e) {}
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
