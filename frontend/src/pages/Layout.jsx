import { Suspense } from "react";

import Header from "@/common/components/header";
import Loading from "@/common/components/loading";

const Layout = ({ children }) => {
  return (
    <>
      <Header />
      <main className="flex flex-col items-center justify-center w-full">
        <Suspense fallback={<Loading />}>{children}</Suspense>
      </main>
    </>
  );
};

export default Layout;
