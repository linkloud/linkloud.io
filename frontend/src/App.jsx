import { GoogleOAuthProvider } from "@react-oauth/google";
import { RouterProvider } from "react-router-dom";
import { HelmetProvider } from "react-helmet-async";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import router from "./routes";
import useAuthStore from "./stores/useAuthStore";

import "./App.css";

const App = () => {
  const initUserInfo = useAuthStore((state) => state.initUserInfo);
  initUserInfo();

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
