import { DetailedHTMLProps, HTMLAttributes } from "react";
import clsx from "clsx";

const styleNames = {
  primary: "bg-primary-high",
  success: "bg-success-high",
  warn: "bg-warn-high",
};

export interface DotBadgeProps
  extends DetailedHTMLProps<HTMLAttributes<HTMLSpanElement>, HTMLSpanElement> {
  styleName?: keyof typeof styleNames;
}

export const DotBadge = ({
  styleName = "primary",
  ...props
}: DotBadgeProps) => {
  return (
    <span
      className={clsx(
        "inline-block h-3 w-3 rounded-full",
        styleNames[styleName]
      )}
      {...props}
    ></span>
  );
};
