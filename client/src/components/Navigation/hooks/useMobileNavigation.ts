import { useState, MouseEvent } from "react";
import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";

const useMobileNavigation = () => {
  const [isAuthModalVisible, setIsAuthModalVisible] = useState(false);

  const userInfo = useAuthStore((state) => state.userInfo);
  const navigate = useNavigate();

  const isAuth = () => {
    return userInfo.role !== "USER";
  };

  const handleAuthModal = (state: boolean) => {
    setIsAuthModalVisible(state);
  };

  const handleRegisterLink = (e: MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault();

    if (userInfo.role === "USER") {
      setIsAuthModalVisible(true);
      return;
    }

    navigate("/links/reg");
  };

  return {
    isAuthModalVisible,
    isAuth,
    handleAuthModal,
    handleRegisterLink,
  };
};

export default useMobileNavigation;
