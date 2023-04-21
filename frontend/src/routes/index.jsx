import Layout from "@/components/layouts";
import HomePage from "@/pages/home";
import ArticleRegPage from "@/pages/article/ArticleReg";
import TagsPage from "@/pages/tags";

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
    path: "/link/reg",
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
];

export default routes;
