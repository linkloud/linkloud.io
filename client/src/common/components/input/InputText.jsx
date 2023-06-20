import { useState } from "react";

const InputText = ({ labelText, className, validMessage, onChange }) => {
  const [isInputFocused, setIsInputFocused] = useState(false); // focus 여부
  const [isInputTouched, setIsInputTouched] = useState(false); // 첫 focus 여부
  const [value, setValue] = useState(""); // 사용자 입력 값

  // input이 foucs거나 text 값이 있다면 top-[-1rem] text-sm text-blue-400
  const labelFocusedClass =
    isInputFocused || value !== ""
      ? "-top-[16px] text-sm text-blue-400"
      : "top-1 text-gray-300";

  const labelClasses = `${labelFocusedClass} ${
    isInputTouched && validMessage ? "text-red-500" : ""
  } absolute left-0 transition-all duration-300`;

  const handleTextChange = (e) => {
    setValue(e.target.value);
    if (!isInputTouched) setIsInputTouched(true);
    onChange(e);
  };

  return (
    <div className={`relative w-full ${className ? className : ""}`}>
      <input
        type="text"
        id={labelText}
        required
        autoComplete="off"
        onFocus={() => setIsInputFocused(true)}
        onBlur={() => setIsInputFocused(false)}
        onChange={handleTextChange}
        className="w-full py-1 bg-transparent border-b border-gray-300 outline-none"
      />
      <label htmlFor={labelText} className={labelClasses}>
        {labelText}
      </label>
      {isInputTouched && validMessage && (
        <p className="mt-2 text-red-400">{validMessage}</p>
      )}
    </div>
  );
};

export default InputText;
