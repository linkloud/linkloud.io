import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";

import Layout from "@/pages/Layout";

// const NotFoundPage = lazy(() => import("@/pages/error/404"));
// const HomePage = lazy(() => import("@/pages/home"));
// const TestPage = lazy(() => import("@/pages/test"));
// const SearchPage = lazy(() => import("@/pages/search"));
// const MemberProfilePage = lazy(() => import("@/pages/members/name"));
// const LinkRegPage = lazy(() => import("@/pages/links/reg"));
// const TagListPage = lazy(() => import("@/pages/tags"));

// import { ROUTES_PATH } from "@/common/constants";

import { privateRoutes } from "./privateRoutes";
import { publicRoutes } from "./publicRoutes";

const router = createBrowserRouter([...privateRoutes, ...publicRoutes]);

export default router;
