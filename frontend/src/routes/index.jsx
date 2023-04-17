import Layout from "@/components/layout";
import HomePage from "@/pages/home";
import ArticleRegPage from "@/pages/article/articleReg";

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
];

export default routes;
