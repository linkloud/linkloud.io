import { Link, LinkProps } from "react-router-dom";
import clsx from "clsx";

import { StrictPropsWithChildren } from "@/types";

export const NavLink = ({
  to,
  onClick,
  className = "",
  children,
}: StrictPropsWithChildren<LinkProps>) => {
  return (
    <Link
      to={to}
      className={clsx(
        "inline-flex justify-center items-center h-10 px-5 font-medium",
        className
      )}
      onClick={onClick}
    >
      {children}
    </Link>
  );
};
