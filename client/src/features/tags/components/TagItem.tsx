import { useState, MouseEventHandler } from "react";

import { XIcon } from "@/assets/svg";
import { ChipProps, Chip } from "@/components/Chip";

export interface TagItemProps {
  name: string;
  size: ChipProps["size"];
  onClick?: MouseEventHandler<HTMLSpanElement>;
  onRemove?: MouseEventHandler<SVGSVGElement>;
}

export const TagItem = ({ name, size, onClick, onRemove }: TagItemProps) => {
  const [isHover, setIsHover] = useState(false);

  const handleHover = (value: boolean) => {
    setIsHover(value);
  };

  return (
    <Chip
      styleName={isHover ? "primary" : "neutral"}
      size={size}
      className="inline-flex transition-colors duration-50 cursor-pointer"
      onClick={onClick}
      onMouseEnter={() => handleHover(true)}
      onMouseLeave={() => handleHover(false)}
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
