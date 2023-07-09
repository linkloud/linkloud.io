import { MouseEventHandler } from "react";

import { CopyIcon } from "@/assets/svg";

export interface CopyButtonProps {
  onClick: MouseEventHandler<HTMLButtonElement>;
}

export const CopyButton = ({ onClick }: CopyButtonProps) => {
  return (
    <button
      aria-label="복사하기 버튼"
      type="button"
      className="flex justify-center items-center h-6 w-6"
      onClick={onClick}
    >
      <CopyIcon className="stroke-neutral-800 h-5 w-5" />
      <span className="sr-only">링크 복사하기</span>
    </button>
  );
};
