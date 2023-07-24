import { useEffect } from "react";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { RouterProvider } from "react-router-dom";
import { HelmetProvider } from "react-helmet-async";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import router from "./routes";
import useAuthStore from "./stores/useAuthStore";

import "./assets/css/style.css";

const App = () => {
  const refresh = useAuthStore((state) => state.refresh);

  useEffect(() => {
    refresh().catch((error) => {
      if (error.message === "Expired refresh token")
        toast.info("세션이 만료되었습니다. 다시 로그인 해주세요.");
    });
  }, [refresh]);

  return (
    <HelmetProvider>
      <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
        <RouterProvider router={router} />
        <ToastContainer />
      </GoogleOAuthProvider>
    </HelmetProvider>
  );
};

export default App;
