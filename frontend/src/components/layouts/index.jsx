import Header from "./Header";
import { Main } from "./style";

const Layout = ({ children }) => {
  return (
    <>
      <Header />
      <Main>{children}</Main>
    </>
  );
};

export default Layout;
