import { Link } from "react-router-dom";
import HeaderNav from "../HeaderNav";

import { LogoLabel } from "@/static/svg";

const Header = () => {
  return (
    <header className="sticky top-0 bg-white border-b-gray-300 z-50">
      <div className="mx-auto px-4 flex justify-between items-center h-20 max-w-6xl">
        <h1>
          <span className="hidden">linkloud</span>
          <Link to="/" aria-label="linkloud home">
            <LogoLabel />
          </Link>
        </h1>
        <HeaderNav></HeaderNav>
      </div>
    </header>
  );
};

export default Header;
