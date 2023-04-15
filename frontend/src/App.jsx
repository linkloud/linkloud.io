import { useState } from "react";
import { ThemeProvider } from "styled-components";
import { GoogleOAuthProvider } from "@react-oauth/google";

// routes
import { useRoutes } from "react-router-dom";
import routes from "./routes";

// style
import GlobalStyle from "./styles/GlobalStyle";
import { LIGHT_THEME } from "./styles";

const App = () => {
  const element = useRoutes(routes);

  // TODO: hooks
  const [theme, setTheme] = useState("light");
  const themeToggler = () => {
    theme === "light" ? setTheme("dark") : setTheme("light");
  };

  return (
    <ThemeProvider theme={theme === "light" ? LIGHT_THEME : "TODO"}>
      <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
        <GlobalStyle />
        {element}
      </GoogleOAuthProvider>
    </ThemeProvider>
  );
};

export default App;
