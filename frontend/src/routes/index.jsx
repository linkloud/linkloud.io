import { createBrowserRouter } from "react-router-dom";
import Layout from "@/pages/Layout";
import HomePage from "@/pages/home";
import SearchPage from "@/pages/search";
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
      links,
      tags,
    ],
  },
]);

export default router;
