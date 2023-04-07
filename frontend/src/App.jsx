import { useState } from "react";
import { ThemeProvider } from "styled-components";
import GlobalStyle from "./styles/GlobalStyle";
import { LIGHT_THEME } from "./styles";

const App = () => {
  // TODO: hooks
  const [theme, setTheme] = useState("light");
  const themeToggler = () => {
    theme === "light" ? setTheme("dark") : setTheme("light");
  };
  return (
    <ThemeProvider theme={theme === "light" ? LIGHT_THEME : "TODO"}>
      <GlobalStyle />
      Hello World
    </ThemeProvider>
  );
};

export default App;
