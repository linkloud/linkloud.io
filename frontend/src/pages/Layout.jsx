import { Outlet } from "react-router-dom";

import Header from "@/common/components/header";

const Layout = () => {
  return (
    <>
      <Header />
      <main className="flex flex-col items-center justify-center w-full">
        <Outlet />
      </main>
    </>
  );
};

export default Layout;
