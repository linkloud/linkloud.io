import { MouseEvent } from "react";
import { useNavigate } from "react-router-dom";

import { useAuthActions } from "@/stores/useAuthStore";
import useModalStore from "@/stores/useModalStore";

import { ROUTE_PATH } from "@/routes/constants";

const useMobileNavigation = () => {
  const { isLoggedIn } = useAuthActions();
  const openModal = useModalStore((state) => state.openModal);
  const navigate = useNavigate();

  const handleClickLink =
    (path: string) => (e: MouseEvent<HTMLAnchorElement>) => {
      e.preventDefault();

      const links = [ROUTE_PATH.LINK.REG, ROUTE_PATH.LIBRARY.LINKS];

      if (!links.includes(path)) return;

      if (!isLoggedIn()) {
        openModal("auth");
        return;
      }

      navigate(path);
    };

  return {
    handleClickLink,
  };
};

export default useMobileNavigation;
