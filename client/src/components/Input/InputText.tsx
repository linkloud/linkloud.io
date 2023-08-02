import clsx from "clsx";
import { useState, InputHTMLAttributes } from "react";

const styleNames = {
  base: "mt-1 border-b transition-colors duration-200",
  focused: "border-primary-500",
  unfocused: "border-gray-200",
};

export interface InputTextProps extends InputHTMLAttributes<HTMLInputElement> {
  id: string;
  label: string;
  placeholder: string;
  value?: string | number;
  required?: boolean;
  errorMessage?: string;
}

export const InputText = ({
  id,
  label,
  placeholder,
  value,
  required = false,
  errorMessage = "",
  ...props
}: InputTextProps) => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <div className="flex flex-col w-full">
      <div>
        <label
          htmlFor={id}
          className="mb-2 flex items-center font-medium text-sm md:text-base"
        >
          {label}
          {required && <span className="ml-1 text-red-500"> *</span>}
        </label>
      </div>
      <div>
        <input
          type="text"
          id={id}
          placeholder={placeholder}
          autoComplete="off"
          value={value}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          {...props}
          className="w-full outline-none md:text-lg"
        />
      </div>
      <span
        className={clsx(
          "mt-1 border-b transition-colors duration-200",
          isFocused ? styleNames.focused : styleNames.unfocused,
        )}
      ></span>

      <p
        className={clsx(
          "md:text-base",
          errorMessage
            ? "animate-shake animate-thrice animate-duration-100 animate-ease-out text-red-500"
            : "text-transparent min-h-[1rem]",
        )}
      >
        {errorMessage || " "}
      </p>
    </div>
  );
};
