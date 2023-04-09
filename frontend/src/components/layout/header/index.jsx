import { Wrap, HeaderContent, HeaderTitle } from "./style";
import HeaderNav from "./nav";
import { LogoLabel } from "@/static/svg";

const Header = () => {
  //TODO: auth state
  const userRole = "GUEST";

  return (
    <Wrap>
      <HeaderContent>
        <HeaderTitle>
          linkloud
          <a href="/">
            <LogoLabel />
          </a>
        </HeaderTitle>
        <HeaderNav role={userRole}></HeaderNav>
      </HeaderContent>
    </Wrap>
  );
};

export default Header;
