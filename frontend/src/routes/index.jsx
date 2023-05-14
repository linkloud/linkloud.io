import { createBrowserRouter } from "react-router-dom";
import Layout from "@/pages/Layout";
import NotFoundPage from "@/pages/error/404";
import HomePage from "@/pages/home";
import SearchPage from "@/pages/search";
import members from "./member";
import links from "./links";
import tags from "./tags";

const router = createBrowserRouter([
  {
    path: "",
    element: <Layout />,
    children: [
      {
        path: "/",
        element: <HomePage />,
      },
      {
        path: "/search",
        element: <SearchPage />,
      },
      members,
      links,
      tags,
    ],
  },
  { path: "*", element: <NotFoundPage /> },
]);

export default router;
