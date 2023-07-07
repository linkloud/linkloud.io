import { HTMLAttributes, PropsWithChildren } from "react";

export const ActionMenu = ({
  children,
  ...props
}: PropsWithChildren<HTMLAttributes<HTMLUListElement>>) => {
  return (
    <ul
      className="flex flex-col gap-2 w-40 p-2 bg-white rounded-md link-shadow-lg z-30"
      {...props}
    >
      {children}
    </ul>
  );
};
