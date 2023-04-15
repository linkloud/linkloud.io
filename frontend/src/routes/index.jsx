import Layout from "@/components/layout";
import Home from "@/pages/home";

const routes = [
  {
    path: "",
    element: (
      <Layout>
        <Home />
      </Layout>
    ),
  },
];

export default routes;
