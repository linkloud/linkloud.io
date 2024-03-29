import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";

import Seo from "@/common/components/SEO";
import Layout from "@/pages/Layout";
import PrivateRoute from "./PrivateRoute";

const NotFoundPage = lazy(() => import("@/pages/error/404"));
const HomePage = lazy(() => import("@/pages/home"));
const SearchPage = lazy(() => import("@/pages/search"));
const MemberProfilePage = lazy(() => import("@/pages/members/name"));
const LinkRegPage = lazy(() => import("@/pages/links/reg"));
const TagListPage = lazy(() => import("@/pages/tags"));

import { ROUTES_PATH } from "@/common/constants";

const router = createBrowserRouter([
  {
    element: <PrivateRoute />,
    children: [
      {
        path: ROUTES_PATH.LINK_REG,
        element: (
          <Layout>
            <LinkRegPage />
          </Layout>
        ),
      },
      // {
      //   path: ROUTES_PATH.MEMBER_PROFILE,
      //   element: <MemberProfilePage />,
      // },
    ],
  },
  {
    path: ROUTES_PATH.HOME,
    index: true,
    element: (
      <Layout>
        <Seo
          title={"Linkloud | 모두의 링크 라이브러리"}
          description={
            "링클라우드는 개발, 디자인, 공부 등 관련된 유용한 링크를 찾고, 저장하고, 공유할 수 있는 서비스입니다."
          }
          type={"website"}
          name={"summary"}
        />
        <HomePage />
      </Layout>
    ),
  },
  {
    path: ROUTES_PATH.SEARCH,
    element: (
      <Layout>
        <SearchPage />
      </Layout>
    ),
  },

  {
    path: ROUTES_PATH.TAGS_LIST,
    element: (
      <Layout>
        <TagListPage />
      </Layout>
    ),
  },
  {
    path: "*",
    element: <NotFoundPage />,
  },
]);

export default router;
