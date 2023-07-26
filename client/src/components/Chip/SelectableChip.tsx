import { MouseEventHandler } from "react";
import clsx from "clsx";
import { StrictPropsWithChildren } from "@/types";
import { ChipProps } from "./Chip";

const styleNames = {
  primary: "text-white bg-primary-high",
  neutral: "text-neutral-600 bg-white active:bg-gray-100",
};

const sizes = {
  sm: "h-6 px-2",
  md: "h-9 px-4",
};

export interface SelectableChipProps extends Omit<ChipProps, "styleName"> {
  isSelected?: boolean;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

export const SelectableChip = ({
  size = "md",
  className = "",
  isSelected = false,
  onClick,
  children,
}: StrictPropsWithChildren<SelectableChipProps>) => {
  const styleName: keyof typeof styleNames = isSelected ? "primary" : "neutral";
  return (
    <span
      className={clsx(
        "flex justify-center items-center font-semibold rounded-full link-shadow-sm cursor-pointer transition-colors",
        styleNames[styleName],
        sizes[size],
        className
      )}
      onClick={onClick}
    >
      {children}
    </span>
  );
};
