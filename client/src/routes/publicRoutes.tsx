import { lazy } from "react";
import { RouteObject } from "react-router-dom";

import Landing from "@/features/misc/pages/Landing";

import { DefaultLayout } from "@/components/Layout";

const Serach = lazy(() => import("@/features/articles/pages/Search"));
const Tags = lazy(() => import("@/features/tags/pages/Tags"));
const NofFound = lazy(() => import("@/features/misc/pages/NotFound"));

export const publicRoutes: RouteObject[] = [
  {
    path: "",
    element: <DefaultLayout />,
    children: [
      {
        path: "/",
        index: true,
        element: <Landing />,
      },
      {
        path: "/search",
        element: <Serach />,
      },
      {
        path: "/tags",
        element: <Tags />,
      },
    ],
  },
  {
    path: "*",
    element: <NofFound />,
  },
];
