import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import useHeader from "./hooks/useHeader";

import { Header } from "./Header";
import { NavLink } from "./NavLink";
import { SearchMenu } from "./SearchMenu";
import { Button } from "../Button";
import { AuthModal } from "../Auth";
import { Avatar } from "../Avatar";
import { ActionMenu, ActionMenuItem } from "../ActionMenu";

import { HeartIcon, InboxIcon, Logo, SearchIcon, UserIcon } from "@/assets/svg";

import { ROUTE_PATH } from "@/routes/constants";

export const DefaultHeader = () => {
  const [isSearchVisible, setIsSearchVisible] = useState(false);

  const {
    userInfo,
    isAuthModalVisible,
    handleAuthModal,
    isActionMenuVisible,
    handleRegisterLink,
    handleClickAvatar,
    handleLeaveAvatar,
    handleLogout,
  } = useHeader();

  const handleCloseSearch = () => {
    setIsSearchVisible(false);
  };

  useEffect(() => {
    if (!isSearchVisible) return;

    const handleScroll = () => {
      setIsSearchVisible((prev) => {
        if (!prev) return false;
        return false;
      });
    };

    window.addEventListener("scroll", handleScroll);

    return () => window.removeEventListener("scroll", handleScroll);
  }, [isSearchVisible]);

  return (
    <>
      <Header layout="between">
        <h1>
          <Link to="/">
            <Logo className="h-14 w-14" />
            <span className="sr-only">링클라우드</span>
          </Link>
        </h1>

        <nav>
          <h1 className="sr-only">네비게이션</h1>
          <ul className="flex gap-2">
            <li className="relative">
              <Button
                name="검색"
                styleName="subtle-neutral"
                size="lg"
                onClick={() => setIsSearchVisible((prev) => !prev)}
              >
                <SearchIcon className="h-[1.25rem] w-[1.25rem] stroke-gray-800"></SearchIcon>
              </Button>
              {isSearchVisible && (
                <SearchMenu
                  onSearch={handleCloseSearch}
                  onClose={handleCloseSearch}
                />
              )}
            </li>
            <li className="hidden md:inline-flex">
              <NavLink to="/links/reg" onClick={(e) => handleRegisterLink(e)}>
                링크 등록
              </NavLink>
            </li>
            {userInfo.role === "USER" && (
              <li>
                <NavLink
                  to="#"
                  className="bg-primary-medium hover:bg-primary-high text-white rounded-md transition-colors"
                  onClick={() => handleAuthModal(true)}
                >
                  로그인
                </NavLink>
                <AuthModal
                  isOpened={isAuthModalVisible}
                  onClose={() => handleAuthModal(false)}
                />
              </li>
            )}
            {userInfo.role !== "USER" && (
              <li
                className="relative px-2 hidden md:block"
                onClick={handleClickAvatar}
              >
                <Avatar
                  aria-expanded={isActionMenuVisible}
                  nickname={userInfo.nickname}
                  profileImage={userInfo.picture}
                />
                {isActionMenuVisible && (
                  <div
                    className="absolute top-0 -right-3/4 pt-14"
                    onMouseLeave={handleLeaveAvatar}
                  >
                    <ActionMenu>
                      <ActionMenuItem to={ROUTE_PATH.LIBRARY.LINKS}>
                        <InboxIcon className="mr-2 h-4 w-4 fill-neutral-800" />
                        내 링크
                      </ActionMenuItem>
                      {/* 
                      <ActionMenuItem to="/profile">
                        <UserIcon className="mr-2 h-4 w-4 stroke-neutral-800" />
                        내정보
                      </ActionMenuItem>
                      <ActionMenuItem to="/likes">
                        <HeartIcon className="mr-2 h-4 w-4 stroke-neutral-800" />
                        좋아요
                      </ActionMenuItem> */}
                      <ActionMenuItem to="#" onClick={handleLogout}>
                        로그아웃
                      </ActionMenuItem>
                    </ActionMenu>
                  </div>
                )}
              </li>
            )}
          </ul>
        </nav>
      </Header>
    </>
  );
};
