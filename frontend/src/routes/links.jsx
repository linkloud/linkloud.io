import LinksRegPage from "@/pages/links/reg";

const links = {
  path: "/links",
  children: [
    {
      path: "reg",
      element: <LinksRegPage />,
    },
  ],
};

export default links;
