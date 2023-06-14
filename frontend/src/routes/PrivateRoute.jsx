import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useAuthStore from "@/stores/useAuthStore";

import { ROUTES_PATH } from "@/common/constants";

const PrivateRoute = () => {
  const { token, isAuthLoading } = useAuthStore((state) => ({
    token: state.token,
    isAuthLoading: state.isAuthLoading,
  }));

  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthLoading && !token) {
      toast.error("로그인이 필요합니다.");
      navigate(ROUTES_PATH.HOME, { replace: true });
    }
  }, [navigate, token, isAuthLoading]);

  return <Outlet />;
};

export default PrivateRoute;
