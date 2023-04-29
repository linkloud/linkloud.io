import Layout from "@/components/layouts";
import HomePage from "@/pages/home";
import ArticleRegPage from "@/pages/article/ArticleReg";
import TagsPage from "@/pages/tags";
import SearchPage from "@/pages/search";

const routes = [
  {
    path: "",
    element: (
      <Layout>
        <HomePage />
      </Layout>
    ),
  },
  {
    path: "/links/reg",
    element: (
      <Layout>
        <ArticleRegPage />
      </Layout>
    ),
  },
  {
    path: "/tags",
    element: (
      <Layout>
        <TagsPage />
      </Layout>
    ),
  },
  {
    path: "/search",
    element: (
      <Layout>
        <SearchPage />
      </Layout>
    ),
  },
];

export default routes;
