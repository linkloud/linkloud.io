import clsx from "clsx";
import { AnchorHTMLAttributes } from "react";

import { userProfile } from "@/assets/images";

const sizes = {
  sm: "x-6 h-6",
  md: "x-10 h-10",
  lg: "x-14 h-14",
};

export interface AvatarProps extends AnchorHTMLAttributes<HTMLAnchorElement> {
  nickname: string;
  profileImage: string | null;
  size?: keyof typeof sizes;
}

export const Avatar = ({
  nickname,
  profileImage,
  size = "md",
  ...props
}: AvatarProps) => {
  return (
    <a className={`bg-gray-200 rounded-full cursor-pointer`} {...props}>
      <img
        src={profileImage || userProfile}
        alt={nickname}
        className={clsx("rounded-full", sizes[size])}
      />
      <span className="hidden">{nickname}</span>
    </a>
  );
};
