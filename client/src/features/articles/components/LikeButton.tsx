import { HeartIcon, HeartFillIcon } from "@/assets/svg";

export const LikeButton = () => {
  return (
    <button
      aria-label="좋아요 버튼"
      type="button"
      className="flex justify-center items-center h-6 w-6"
    >
      <HeartIcon className="stroke-neutral-800 h-5 w-5" />
      <span className="sr-only">좋아요</span>
    </button>
  );
};
