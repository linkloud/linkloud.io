import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useAuthStore from "@/stores/useAuthStore";

import { ROUTES_PATH } from "@/common/constants";

const PrivateRoute = () => {
  const { token } = useAuthStore();
  const navigate = useNavigate();

  useEffect(() => {
    if (!token) {
      toast.error("로그인이 필요합니다.");
      navigate(ROUTES_PATH.HOME, { replace: true });
    }
  }, [navigate]);

  return <Outlet />;
};

export default PrivateRoute;
