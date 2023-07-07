import { MouseEvent, MouseEventHandler } from "react";

import { ChipProps } from "@/components/Chip";
import { Chip } from "@/components/Chip";

import { XIcon } from "@/assets/svg";

export interface TagItemProps {
  name: string;
  size: ChipProps["size"];
  onClick?: MouseEventHandler<HTMLSpanElement>;
  onRemove?: MouseEventHandler<SVGSVGElement>;
}

export const TagItem = ({ name, size, onClick, onRemove }: TagItemProps) => {
  return (
    <Chip
      styleName="neutral"
      size={size}
      className="inline-flex  hover:text-primary-medium hover:bg-primary-low transition-colors duration-300 cursor-pointer"
      onClick={onClick}
    >
      #{name}{" "}
      {onRemove && (
        <XIcon
          onClick={onRemove}
          className="ml-1 my-auto h-4 w-4 stroke-neutral-600"
        />
      )}
    </Chip>
  );
};
