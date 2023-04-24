import HeaderNav from "../HeaderNav";

import { LogoLabel } from "@/static/svg";

const Header = () => {
  //TODO: auth state
  const userRole = "GUEST";

  return (
    <header className="sticky top-0 bg-white border-b-gray-300">
      <div className="mx-auto px-4 flex justify-between items-center h-20 max-w-6xl">
        <h1>
          <span className="hidden">linkloud</span>
          <a href="/" aria-label="linkloud home">
            <LogoLabel />
          </a>
        </h1>
        <HeaderNav role={userRole}></HeaderNav>
      </div>
    </header>
  );
};

export default Header;
