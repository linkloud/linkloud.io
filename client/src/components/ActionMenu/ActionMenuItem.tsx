import { Link, LinkProps } from "react-router-dom";

import { StrictPropsWithChildren } from "@/types";

export const ActionMenuItem = ({
  children,
  to,
  ...props
}: StrictPropsWithChildren<LinkProps>) => {
  return (
    <li>
      <Link
        to={to}
        className="flex items-center px-3 py-2 hover:bg-gray-100 rounded-md cursor-pointer"
        {...props}
      >
        {children}
      </Link>
    </li>
  );
};
