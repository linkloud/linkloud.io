import React, { useState, useRef } from "react";

const styleClasses = {
  base: "mt-1 border-b transition-colors duration-200",
  focused: "border-primary-500",
  unfocused: "border-gray-200",
};

const InputText = ({
  id,
  label,
  placeholder,
  value,
  required = false,
  errorMessage = "",
  ...props
}) => {
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
        className={`${styleClasses.base} ${
          isFocused ? styleClasses.focused : styleClasses.unfocused
        }`}
      ></span>
      {errorMessage && (
        <p className="mt-2 md:text-base text-red-500">{errorMessage}</p>
      )}
    </div>
  );
};

export default InputText;
