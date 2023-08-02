import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import {
  HeartIcon,
  InboxIcon,
  LogoIcon,
  SearchIcon,
  UserIcon,
} from "@/assets/svg";
import { ROUTE_PATH } from "@/routes/constants";

import { ActionMenu, ActionMenuItem } from "../ActionMenu";
import { Avatar } from "../Avatar";
import { Button } from "../Button";

import { HeaderLayout } from "./HeaderLayout";
import useHeader from "./hooks/useHeader";
import { NavLink } from "./NavLink";
import { SearchMenu } from "./SearchMenu";

export const DefaultHeader = () => {
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);
  const [isSearchVisible, setIsSearchVisible] = useState(false);

  const {
    user,
    isLoggedIn,
    handleClickLogin,
    handleRegisterLink,
    handleLogout,
  } = useHeader();

  const handleClickAvatar = () => {
    setIsActionMenuVisible(true);
  };

  const handleLeaveAvatar = () => {
    setIsActionMenuVisible(false);
  };

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
      <HeaderLayout layout="between">
        <h1>
          <Link to="/">
            <LogoIcon className="h-14 w-14" />
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
            {isLoggedIn() && (
              <li
                className="relative px-2 hidden md:block"
                onClick={handleClickAvatar}
              >
                <Avatar
                  aria-expanded={isActionMenuVisible}
                  nickname={user.nickname}
                  profileImage={user.picture}
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
                        내 정보
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
            {!isLoggedIn() && (
              <li>
                <NavLink
                  to="#"
                  className="bg-primary-medium hover:bg-primary-high text-white rounded-md transition-colors"
                  onClick={handleClickLogin}
                >
                  로그인
                </NavLink>
              </li>
            )}
          </ul>
        </nav>
      </HeaderLayout>
    </>
  );
};
