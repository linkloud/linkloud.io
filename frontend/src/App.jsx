import { GoogleOAuthProvider } from "@react-oauth/google";

// routes
import { useRoutes } from "react-router-dom";
import routes from "./routes";

// style
import "./App.css";

const App = () => {
  const element = useRoutes(routes);

  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
      {element}
    </GoogleOAuthProvider>
  );
};

export default App;
