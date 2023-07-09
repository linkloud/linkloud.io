import { ReactNode, Suspense } from "react";
import { Outlet } from "react-router-dom";

import { DefaultHeader } from "../Header";
import { Spinner } from "../Spinner";

export const DefaultLayout = () => {
  return (
    <>
      <DefaultHeader />
      <main className="flex flex-col items-center h-full w-full">
        <Suspense fallback={<Spinner />}>
          <Outlet />
        </Suspense>
      </main>
    </>
  );
};
