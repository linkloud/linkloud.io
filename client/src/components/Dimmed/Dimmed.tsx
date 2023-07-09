import { StrictPropsWithChildren } from "@/types";

export const Dimmed = ({ children }: StrictPropsWithChildren) => {
  return (
    <div className="fixed flex justify-center inset-0 bg-black bg-opacity-20 z-20">
      {children}
    </div>
  );
};
