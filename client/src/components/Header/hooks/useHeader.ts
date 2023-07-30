import { useState, MouseEvent } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useAuthStore from "@/stores/useAuthStore";
import useModalStore from "@/stores/useModalStore";

import { ROUTE_PATH } from "@/routes/constants";

const useHeader = () => {
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const userInfo = useAuthStore((state) => state.userInfo);
  const logout = useAuthStore((state) => state.logout);
  const openModal = useModalStore((state) => state.openModal);
  const navigate = useNavigate();

  const handleClickLogin = () => {
    openModal("auth");
  };

  const handleRegisterLink = (e: MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault();

    if (userInfo.role === "USER") {
      openModal("auth");
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
    handleClickLogin,
    isActionMenuVisible,
    handleRegisterLink,
    handleClickAvatar,
    handleLeaveAvatar,
    handleLogout,
  };
};

export default useHeader;
