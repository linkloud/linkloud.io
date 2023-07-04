import { AnchorHTMLAttributes } from "react";
import clsx from "clsx";
import { StrictPropsWithChildren } from "@/types";

const styleNames = {
  primary: "text-white bg-primary-high active:bg-primary-medium",
  neutral: "active:bg-neutral-100",
};

export interface CapsuleButtonProps
  extends AnchorHTMLAttributes<HTMLAnchorElement> {
  styleName?: keyof typeof styleNames;
}

export const CapsuleButton = ({
  styleName = "primary",
  className,
  children,
  ...props
}: StrictPropsWithChildren<CapsuleButtonProps>) => {
  return (
    <a
      className={clsx(
        "flex justify-center items-center h-10 px-2.5 rounded-full link-shadow-lg cursor-pointer",
        styleNames[styleName],
        className
      )}
      {...props}
    >
      {children}
    </a>
  );
};
