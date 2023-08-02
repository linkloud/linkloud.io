import clsx from "clsx";

import { LoadingIcon } from "@/assets/svg";

const styleNames = {
  primary: "fill-primary-high",
  light: "fill-white",
};

const sizes = {
  sm: "h-4 w-4",
  md: "h-8 w-8",
  lg: "h-16 w-16",
  xl: "h-24 w-24",
};

export interface SpinnerProps {
  styleName?: keyof typeof styleNames;
  size?: keyof typeof sizes;
  className?: string;
}

export const Spinner = ({
  styleName = "primary",
  size = "md",
  className = "",
}: SpinnerProps) => {
  return (
    <div>
      <LoadingIcon
        className={clsx(
          "animate-spin opacity-90",
          styleNames[styleName],
          sizes[size],
          className,
        )}
      />
      <span className="sr-only">loading</span>
    </div>
  );
};
