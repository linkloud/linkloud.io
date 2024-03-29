import { Link } from "react-router-dom";

import useHeader from "./hooks/useHeader";

import HeaderActionMenu from "./HeaderActionMenu";
import Button from "../button";
import UserProfile from "../user/UserProfile";
import AuthLoginModal from "../auth/AuthLoginModal";
import { Logo } from "@/static/svg";

import { ROLE } from "@/common/constants";

const Header = () => {
  const {
    userInfo,
    isScrollTop,
    isActionMenuVisible,
    isLoginModalOpened,
    handleOpenLoginModal,
    handleCloseLoginModal,
    handleRegisterLink,
    handleClickNotice,
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
          <Link to="/" aria-label="linkloud" className="flex items-end">
            <Logo />
            <span className="hidden md:inline-block ml-2 font-semibold text-2xl">
              Linkloud
            </span>
          </Link>
        </h1>
        <nav>
          <h1 className="hidden">navigation</h1>
          <ul className="flex items-center">
            {role !== ROLE.USER && (
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
            {role === ROLE.USER ? (
              <li className="ml-2">
                <Button
                  size="md"
                  styleType="fill"
                  aria-haspopup="dialog"
                  onClick={handleOpenLoginModal}
                >
                  로그인
                </Button>
                <AuthLoginModal
                  isOpened={isLoginModalOpened}
                  onCloseLoginModal={handleCloseLoginModal}
                />
              </li>
            ) : (
              <li className="relative px-4">
                <UserProfile
                  name={nickname}
                  profileImage={picture}
                  onClick={handleClickProfile}
                />
                {isActionMenuVisible && (
                  <HeaderActionMenu
                    nickname={nickname}
                    handleMouseLeave={handleLeaveProfile}
                  />
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
