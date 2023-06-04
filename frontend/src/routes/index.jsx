import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";

import Layout from "@/pages/Layout";

const NotFoundPage = lazy(() => import("@/pages/error/404"));
const HomePage = lazy(() => import("@/pages/home"));
const SearchPage = lazy(() => import("@/pages/search"));
const MemberProfilePage = lazy(() => import("@/pages/members/name"));
const LinkRegPage = lazy(() => import("@/pages/links/reg"));
const TagListPage = lazy(() => import("@/pages/tags"));

import { ROUTES_PATH } from "@/common/constants";

const router = createBrowserRouter([
  {
    element: <Layout />,
    children: [
      {
        path: ROUTES_PATH.HOME,
        index: true,
        element: <HomePage />,
      },
      {
        path: ROUTES_PATH.SEARCH,
        element: <SearchPage />,
      },
      // {
      //   path: ROUTES_PATH.MEMBER_PROFILE,
      //   element: <MemberProfilePage />,
      // },
      {
        path: ROUTES_PATH.LINK_REG,
        element: <LinkRegPage />,
      },
      {
        path: ROUTES_PATH.TAGS_LIST,
        element: <TagListPage />,
      },
    ],
  },
  { path: "*", element: <NotFoundPage /> },
]);

export default router;
