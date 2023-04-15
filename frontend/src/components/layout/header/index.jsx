import { Wrap, HeaderContent, HeaderTitle } from "./style";
import HeaderNav from "./headerNav";
import { LogoLabel } from "@/static/svg";

const Header = () => {
  //TODO: auth state
  const userRole = "GUEST";

  return (
    <Wrap>
      <HeaderContent>
        <HeaderTitle>
          linkloud
          <a href="/" aria-label="linkloud home">
            <LogoLabel />
          </a>
        </HeaderTitle>
        <HeaderNav role={userRole}></HeaderNav>
      </HeaderContent>
    </Wrap>
  );
};

export default Header;
