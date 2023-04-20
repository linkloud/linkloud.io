import Layout from "@/components/layout";
import HomePage from "@/pages/home";
import ArticleRegPage from "@/pages/article/articleReg";
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
