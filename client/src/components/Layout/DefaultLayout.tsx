import { Suspense } from "react";
import { Outlet } from "react-router-dom";

import { DefaultHeader } from "../Header";
import { MobileNavigation } from "../Navigation";
import { Spinner } from "../Spinner";
import { Modals } from "../Modal";

export const DefaultLayout = () => {
  return (
    <>
      <DefaultHeader />
      <main className="flex flex-col items-center w-full">
        <Suspense fallback={<Spinner />}>
          <Outlet />
        </Suspense>
      </main>
      <MobileNavigation />
      <Modals />
    </>
  );
};
