import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

import HeaderActionMenu from "./HeaderActionMenu";
import Button from "../button";
import UserProfile from "../user/UserProfile";

import { LogoLabel, Logo } from "@/static/svg";

import useUserStore from "@/stores/useUserStore";
import useModalStore from "@/stores/useModalStore";

const Header = () => {
  const navigate = useNavigate();
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const { userRole, name, profileImage } = useUserStore((state) => ({
    name: state.name,
    userRole: state.role,
    profileImage: state.profileImage,
  }));

  const { openModal } = useModalStore();

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
    <header className="sticky top-0 bg-white border-b-gray-300 z-50">
      <div className="mx-auto px-4 flex justify-between items-center h-16 md:h-20 max-w-6xl">
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
