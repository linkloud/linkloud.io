import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

import useUserStore from "@/stores/useUserStore";
import useModalStore from "@/stores/useModalStore";

import HeaderActionMenu from "./HeaderActionMenu";
import Button from "../button";
import UserProfile from "../user/UserProfile";
import { LogoLabel, Logo } from "@/static/svg";

import { throttle } from "@/common/utils";

const Header = () => {
  const navigate = useNavigate();

  const { userRole, name, profileImage } = useUserStore((state) => ({
    name: state.name,
    userRole: state.role,
    profileImage: state.profileImage,
  }));
  const { openModal } = useModalStore();
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);
  const [isScrollTop, setIsScrollTop] = useState(true);

  useEffect(() => {
    document.addEventListener("scroll", throttledHandleScroll);
    return () => {
      document.removeEventListener("scroll", throttledHandleScroll);
    };
  }, [isScrollTop]);

  const throttledHandleScroll = throttle(() => {
    if (window.scrollY > 30) {
      setIsScrollTop(false);
    } else if (window.scrollY <= 30) {
      setIsScrollTop(true);
    }
  }, 50);

  // 로그인 모달
  const handleOpenLoginModal = () => openModal("login");

  // 링크 등록
  const handleRegisterLink = () => {
    if (!userRole || userRole === "guest") {
      setOpen("login");
      return;
    }
    navigate("/links/reg");
  };

  const handleHoverProfile = () => {
    setIsActionMenuVisible(true);
  };

  const handleLeaveProfile = () => {
    setIsActionMenuVisible(false);
  };

  return (
    <header
      className={`sticky top-0 bg-white z-50 transition-all duration-100${
        !isScrollTop && " shadow-md"
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
            <li className="mr-2 hidden md:block">
              <Button
                size="md"
                styleType="subtle"
                aria-haspopup="dialog"
                onClick={console.log("공지사항")}
              >
                공지사항
              </Button>
            </li>
            {userRole === "admin" && (
              <li className="mr-2">
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
            {userRole !== "guest" && (
              <li className="mr-2">
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
            {userRole === "guest" ? (
              <li className="mr-2">
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
                  name={name}
                  profileImage={profileImage}
                  onClick={handleHoverProfile}
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
