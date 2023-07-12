import { lazy, useEffect } from "react";
import { RouteObject, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useAuthStore from "@/stores/useAuthStore";

import { DefaultLayout } from "@/components/Layout";
import { StrictPropsWithChildren } from "@/types";

const LinksReg = lazy(() => import("@/features/articles/pages/Reg"));

import { ROUTE_PATH } from "./constants";

const PrivateRoute = ({ children }: StrictPropsWithChildren) => {
  const { token, isAuthLoading } = useAuthStore((state) => ({
    token: state.token,
    isAuthLoading: state.loading,
  }));

  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthLoading && !token) {
      toast.error("로그인이 필요합니다.");
      navigate("/", { replace: true });
    }
  }, [token, isAuthLoading]);

  return children;
};

export const privateRoutes: RouteObject[] = [
  {
    path: "",
    element: (
      <PrivateRoute>
        <DefaultLayout />
      </PrivateRoute>
    ),
    children: [
      {
        path: ROUTE_PATH.LINK.REG,
        index: true,
        element: <LinksReg />,
      },
    ],
  },
];
