import { useState, MouseEvent } from "react";
import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";

import { ROUTE_PATH } from "@/routes/constants";

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

  const handleClickLink =
    (path: string) => (e: MouseEvent<HTMLAnchorElement>) => {
      e.preventDefault();

      const links = [ROUTE_PATH.LINK.REG, ROUTE_PATH.LIBRARY.LINKS];

      if (!links.includes(path)) return;

      if (userInfo.role === "USER") {
        setIsAuthModalVisible(true);
        return;
      }

      navigate(path);
    };

  return {
    isAuthModalVisible,
    isAuth,
    handleAuthModal,
    handleClickLink,
  };
};

export default useMobileNavigation;
