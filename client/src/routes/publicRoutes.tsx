import { lazy } from "react";
import { RouteObject } from "react-router-dom";

import { DefaultLayout } from "@/components/Layout";
import Landing from "@/features/misc/pages/Landing";

import { ROUTE_PATH } from "./constants";

const Serach = lazy(() => import("@/features/articles/pages/Search"));
const Tags = lazy(() => import("@/features/tags/pages/Tags"));
const NofFound = lazy(() => import("@/features/misc/pages/NotFound"));

export const publicRoutes: RouteObject[] = [
  {
    path: "",
    element: <DefaultLayout />,
    children: [
      {
        path: ROUTE_PATH.LANDING,
        index: true,
        element: <Landing />,
      },
      {
        path: ROUTE_PATH.SEARCH,
        element: <Serach />,
      },
      {
        path: ROUTE_PATH.TAG.LIST,
        element: <Tags />,
      },
    ],
  },
  {
    path: "*",
    element: <NofFound />,
  },
];
