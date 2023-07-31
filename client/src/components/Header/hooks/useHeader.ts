import { useEffect, MouseEvent } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useModalStore from "@/stores/useModalStore";
import { useUser, useAuthActions } from "@/stores/useAuthStore";

import { ROUTE_PATH } from "@/routes/constants";

const useHeader = () => {
  const user = useUser();
  const authActions = useAuthActions();
  const openModal = useModalStore((state) => state.openModal);
  const navigate = useNavigate();

  const handleClickLogin = () => {
    openModal("auth");
  };

  const handleRegisterLink = (e: MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault();

    if (!authActions.isLoggedIn()) {
      openModal("auth");
      return;
    }

    navigate(ROUTE_PATH.LINK.REG);
  };

  const handleLogout = () => {
    authActions.logout();
    navigate(ROUTE_PATH.LANDING);
  };

  useEffect(() => {
    if (user.role !== "USER") {
      authActions.refresh().then(({ success, error }) => {
        if (error) authActions.reset();

        if (error?.message === "Expired refresh token") {
          toast("로그인이 만료 되었습니다", { autoClose: 3000 });
          navigate(ROUTE_PATH.LANDING);
        }
      });
    }
  }, [user.role, authActions.refresh, navigate]);

  return {
    user,
    isLoggedIn: authActions.isLoggedIn,
    handleClickLogin,
    handleRegisterLink,
    handleLogout,
  };
};

export default useHeader;
