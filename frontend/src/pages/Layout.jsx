import { Outlet } from "react-router-dom";

import GlobalModalContainer from "./GlobalModalContainer";
import Header from "@/common/components/header";

const Layout = () => {
  return (
    <>
      <Header />
      <GlobalModalContainer />
      <main className="flex flex-col items-center justify-center w-full">
        <Outlet />
      </main>
    </>
  );
};

export default Layout;
