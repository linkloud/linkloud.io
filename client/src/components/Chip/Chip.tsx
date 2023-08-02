import clsx from "clsx";
import { DetailedHTMLProps, HTMLAttributes } from "react";

import { StrictPropsWithChildren } from "@/types";

const styleNames = {
  primary: "text-white bg-primary-high",
  neutral: "text-neutral-600 bg-neutral-100",
};

const sizes = {
  sm: "h-6 px-2",
  md: "h-8 px-3.5",
};

export interface ChipProps
  extends DetailedHTMLProps<HTMLAttributes<HTMLSpanElement>, HTMLSpanElement> {
  styleName?: keyof typeof styleNames;
  size?: keyof typeof sizes;
  className?: string;
}

export const Chip = ({
  styleName = "primary",
  size = "md",
  className = "",
  children,
  ...props
}: StrictPropsWithChildren<ChipProps>) => {
  return (
    <span
      className={clsx(
        "flex justify-center items-center rounded",
        styleNames[styleName],
        sizes[size],
        className,
      )}
      {...props}
    >
      {children}
    </span>
  );
};
