import clsx from "clsx";
import { DetailedHTMLProps, HTMLAttributes } from "react";

import { StrictPropsWithChildren } from "@/types";

const styleNames = {
  primary: "text-white bg-primary-high",
  "primary-inverted": "text-primary-high",
  success: "text-white bg-success-high",
  "success-inverted": "text-success-high",
  warn: "text-white bg-warn-high",
  "warn-inverted": "text-warn-high",
};

export interface BadgeProps
  extends DetailedHTMLProps<HTMLAttributes<HTMLSpanElement>, HTMLSpanElement> {
  styleName?: keyof typeof styleNames;
}

export const Badge = ({
  styleName = "primary",
  className = "",
  children,
  ...props
}: StrictPropsWithChildren<BadgeProps>) => {
  return (
    <span
      className={clsx(
        "py-1 px-2 rounded-full text-xs font-semibold",
        styleNames[styleName],
        className,
      )}
      {...props}
    >
      {children}
    </span>
  );
};
