import { StrictPropsWithChildren } from "@/types";
import { MouseEventHandler } from "react";

export interface DimmedProps {
  onClick?: MouseEventHandler<HTMLDivElement>;
}

export const Dimmed = ({
  onClick,
  children,
}: StrictPropsWithChildren<DimmedProps>) => {
  return (
    <div
      onClick={onClick}
      className="fixed inset-0 w-full h-screen cursor-pointer bg-black opacity-30 z-10"
    >
      {children}
    </div>
  );
};
