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
      className="fixed flex justify-center inset-0 cursor-pointer bg-black bg-opacity-20 z-20"
    >
      {children}
    </div>
  );
};
