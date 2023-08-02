import { lazy } from "react";
import { RouteObject, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import { DefaultLayout } from "@/components/Layout";
import { useAuthActions } from "@/stores/useAuthStore";
import { StrictPropsWithChildren } from "@/types";

import { ROUTE_PATH } from "./constants";

const LinksReg = lazy(() => import("@/features/articles/pages/Reg"));
const LibraryLinks = lazy(() => import("@/features/members/pages/Links"));

const PrivateRoute = ({ children }: StrictPropsWithChildren) => {
  const { isLoggedIn } = useAuthActions();
  const navigate = useNavigate();

  if (!isLoggedIn()) {
    toast.error("로그인이 필요합니다.", { autoClose: 3000 });
    navigate("/", { replace: true });
  }

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
        element: <LinksReg />,
      },
      {
        path: ROUTE_PATH.LIBRARY.LINKS,
        element: <LibraryLinks />,
      },
    ],
  },
];
