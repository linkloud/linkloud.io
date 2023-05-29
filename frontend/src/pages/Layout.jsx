import { Suspense } from "react";
import { Outlet } from "react-router-dom";

import Header from "@/common/components/header";
import Loading from "@/common/components/loading";

const Layout = () => {
  return (
    <Suspense fallback={<Loading />}>
      <Header />
      <main className="flex flex-col items-center justify-center w-full">
        <Outlet />
      </main>
    </Suspense>
  );
};

export default Layout;
