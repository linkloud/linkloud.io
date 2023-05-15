import { Link } from "react-router-dom";

import useHeader from "./hooks/useHeader";

import HeaderActionMenu from "./HeaderActionMenu";
import Button from "../button";
import UserProfile from "../user/UserProfile";
import { LogoLabel, Logo } from "@/static/svg";

import { ROLE } from "@/common/constants";

const Header = () => {
  const {
    userInfo,
    isScrollTop,
    isActionMenuVisible,
    handleOpenLoginModal,
    handleRegisterLink,
    handleClickProfile,
    handleLeaveProfile,
  } = useHeader();

  const { role, picture, nickname } = userInfo;

  return (
    <header
      className={`sticky top-0 bg-white z-50 transition-all ${
        isScrollTop ? "" : "shadow-md"
      }`}
    >
      <div className="mx-auto px-6 flex justify-between items-center h-16 max-w-7xl">
        <h1>
          <span className="hidden">linkloud</span>
          <Link to="/" aria-label="linkloud home">
            <LogoLabel className="hidden md:inline" />
            <Logo className="md:hidden" />
          </Link>
        </h1>
        <nav>
          <h1 className="hidden">navigation</h1>
          <ul className="flex items-center">
            <li className="ml-2 hidden md:block">
              <Button
                size="md"
                styleType="subtle"
                aria-haspopup="dialog"
                onClick={console.log("공지사항")}
              >
                공지사항
              </Button>
            </li>
            {role === ROLE.ADMIN && (
              <li className="ml-2">
                <Button
                  size="md"
                  styleType="inverted"
                  aria-haspopup="dialog"
                  onClick={console.log("태그등록")}
                >
                  태그 등록
                </Button>
              </li>
            )}
            {role !== ROLE.GUEST && (
              <li className="ml-2">
                <Button
                  size="md"
                  styleType="subtle"
                  aria-haspopup="dialog"
                  onClick={handleRegisterLink}
                >
                  링크 등록
                </Button>
              </li>
            )}
            {role === ROLE.GUEST ? (
              <li className="ml-2">
                <Button
                  size="md"
                  styleType="fill"
                  aria-haspopup="dialog"
                  onClick={handleOpenLoginModal}
                >
                  로그인
                </Button>
              </li>
            ) : (
              <li className="relative px-2">
                <UserProfile
                  name={nickname}
                  profileImage={picture}
                  onClick={handleClickProfile}
                />
                {isActionMenuVisible && (
                  <HeaderActionMenu handleMouseLeave={handleLeaveProfile} />
                )}
              </li>
            )}
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;
