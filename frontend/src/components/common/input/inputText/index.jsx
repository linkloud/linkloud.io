import { useState } from "react";

const InputText = ({ labelText }) => {
  const [isInputFocused, setIsInputFocused] = useState(false);
  const [text, setText] = useState("");

  // input이 foucs거나 text 값이 있다면 top-[-16px] text-sm text-blue-400
  const labelFocusedClass =
    isInputFocused || text !== ""
      ? "top-[-16px] text-sm text-blue-400"
      : "text-gray-300";

  const onChange = (e) => {
    console.log("");
    setText(e.target.value);
    console.log(text);
  };

  return (
    <div className="relative w-full">
      <input
        type="text"
        id={labelText}
        required
        onFocus={() => setIsInputFocused(true)}
        onBlur={() => setIsInputFocused(false)}
        onChange={onChange}
        className="w-full py-1 bg-transparent border-b-2 outline-none"
      />
      <label
        htmlFor={labelText}
        className={`${labelFocusedClass} absolute top-1 left-0 transition-all duration-300`}
      >
        {labelText}
      </label>
    </div>
  );
};

export default InputText;
