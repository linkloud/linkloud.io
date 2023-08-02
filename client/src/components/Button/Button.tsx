import clsx from "clsx";
import { ButtonHTMLAttributes } from "react";

import { StrictPropsWithChildren } from "@/types";

import { Spinner, SpinnerProps } from "../Spinner";

const styleNames = {
  solid:
    "text-white bg-primary-high active:bg-primary-medium border-transparent",
  "solid-warn": "text-white bg-warn-medium active:bg-warn-high",
  outline:
    "border-primary-high bg-white active:bg-neutral-100 text-primary-high",
  "outline-neutral": "border-neutral-200 bg-white active:bg-neutral-100",
  "outline-warn":
    "border-warn-medium bg-white active:bg-neutral-100 text-warn-medium",
  subtle: "text-primary-high border-transparent",
  "subtle-neutral": "border-transparent",
};

const sizes = {
  xl: "h-12 px-7",
  lg: "h-10 px-5",
  md: "h-8 px-3.5",
  sm: "h-7 px-2.5 text-sm",
  xs: "h-6 px-2 text-sm",
};

export interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  name: string;
  styleName?: keyof typeof styleNames;
  size?: keyof typeof sizes;
  isLoading?: boolean;
}

export const Button = ({
  name,
  type = "button",
  styleName = "solid",
  size = "md",
  isLoading = false,
  className = "",
  children,
  ...props
}: StrictPropsWithChildren<ButtonProps>) => {
  const spinnerStyle: SpinnerProps["styleName"] =
    styleName === "solid" || styleName === "solid-warn" ? "light" : "primary";
  const spinnerSize: SpinnerProps["size"] =
    size === "sm" || size === "xs" ? "sm" : "md";

  return (
    <button
      aria-label={name}
      type={type}
      className={clsx(
        "flex justify-center items-center disabled:opacity-70 disabled:cursor-not-allowed border rounded-md font-medium",
        sizes[size],
        styleNames[styleName],
        className,
      )}
      {...props}
    >
      {!isLoading && children}
      {isLoading && <Spinner styleName={spinnerStyle} size={spinnerSize} />}
    </button>
  );
};
