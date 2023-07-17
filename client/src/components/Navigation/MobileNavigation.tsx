import { HTMLAttributes } from "react";
import { Link, useLocation } from "react-router-dom";
import clsx from "clsx";

import useMobileNavigation from "./hooks/useMobileNavigation";

import {
  HomeIcon,
  HomeFillIcon,
  InboxIcon,
  InboxFillIcon,
  HeartIcon,
  HeartFillIcon,
  PlusIcon,
  UserIcon,
  UserFillIcon,
} from "@/assets/svg";
import { AuthModal } from "../Auth";

import { ROUTE_PATH } from "@/routes/constants";

const activeColor = "stroke-primary-high fill-primary-high";
const inactiveColor = "fill-gray-400";

const links = [
  {
    id: 1,
    name: "홈",
    to: ROUTE_PATH.LANDING,
    inactive: <HomeIcon className={inactiveColor} />,
    active: <HomeFillIcon className={activeColor} />,
  },
  {
    id: 2,
    name: "내 링크",
    to: ROUTE_PATH.LIBRARY.LINKS,
    inactive: <InboxIcon className={inactiveColor} />,
    active: <InboxFillIcon className={activeColor} />,
  },
  {
    id: 3,
    name: "등록",
    to: ROUTE_PATH.LINK.REG,
    inactive: <PlusIcon className={inactiveColor} />,
    active: <PlusIcon className={activeColor} />,
  },
  // {
  //   id: 4,
  //   name: "좋아요",
  //   to: "/library/likes",
  //   inactive: <HeartIcon className={inactiveColor} />,
  //   active: <HeartFillIcon className={activeColor} />,
  // },
  // {
  //   id: 5,
  //   name: "프로필",
  //   to: "/profile",
  //   inactive: <UserIcon className={inactiveColor} />,
  //   active: <UserFillIcon className={activeColor} />,
  // },
];

export interface NavigationProps extends HTMLAttributes<HTMLElement> {}

export const MobileNavigation = ({ ...props }: NavigationProps) => {
  const location = useLocation();
  const { isAuthModalVisible, isAuth, handleAuthModal, handleClickLink } =
    useMobileNavigation();

  return (
    <nav
      className="md:hidden fixed bottom-0 flex justify-center px-6 h-14 w-full border-t border-neutral-200 bg-white"
      {...props}
    >
      <h1 className="sr-only">모바일 네비게이션</h1>
      <ul className="flex justify-center w-full">
        {links.map((link) => (
          <li key={link.id} className="flex justify-center items-center w-full">
            <Link
              to={link.to}
              className={clsx(
                "flex flex-col justify-center items-center h-full w-full"
              )}
              onClick={link.id !== 1 ? handleClickLink(link.to) : undefined}
            >
              {location.pathname === link.to ? link.active : link.inactive}
              <span className="sr-only">{link.name}</span>
            </Link>
          </li>
        ))}
      </ul>
      <AuthModal
        isOpened={isAuthModalVisible}
        onClose={() => handleAuthModal(false)}
      />
    </nav>
  );
};
