import { useState } from "react";
import { ThemeProvider } from "styled-components";
import { GoogleOAuthProvider } from "@react-oauth/google";
import GlobalStyle from "./styles/GlobalStyle";
import { LIGHT_THEME } from "./styles";
import Header from "./components/layout/header";
import Home from "./pages/home";

const App = () => {
  // TODO: hooks
  const [theme, setTheme] = useState("light");
  const themeToggler = () => {
    theme === "light" ? setTheme("dark") : setTheme("light");
  };

  return (
    <ThemeProvider theme={theme === "light" ? LIGHT_THEME : "TODO"}>
      <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
        <GlobalStyle />
        <Header></Header>
        <Home></Home>
      </GoogleOAuthProvider>
    </ThemeProvider>
  );
};

export default App;
