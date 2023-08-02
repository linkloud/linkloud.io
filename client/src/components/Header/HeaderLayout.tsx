import clsx from "clsx";

import { StrictPropsWithChildren } from "@/types";

const layouts = {
  start: "flex items-center",
  end: "flex items-center justify-end",
  between: "flex items-center justify-between",
  grid: "grid grid-flow-col",
};

export interface HeaderLayoutProps {
  layout?: keyof typeof layouts;
}

export const HeaderLayout = ({
  layout = "start",
  children,
}: StrictPropsWithChildren<HeaderLayoutProps>) => {
  return (
    <header className="sticky top-0 h-20 bg-white border-b border-neutral-100 z-10 transition-all">
      <div
        className={clsx(
          "mx-auto px-6 h-full w-full max-w-7xl",
          layouts[layout],
        )}
      >
        {children}
      </div>
    </header>
  );
};
