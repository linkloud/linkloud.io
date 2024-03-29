const Button = ({
  type = "button",
  size = "md",
  styleType = "fill",
  className = "",
  disabled = false,
  onClick,
  children,
  ...props
}) => {
  const sizeTable = {
    xs: "px-2 h-6 text-sm",
    sm: "px-4 h-8 text-sm",
    md: "px-5 h-10",
    lg: "px-7 h-12",
  };

  const styleTable = {
    subtle: "rounded-md hover:bg-gray-100 transition-colors",
    lined: "rounded-md border border-gray-300 bg-white hover:border-gray-400",
    fill: "rounded-md text-white bg-primary-600 hover:bg-primary-500 transition-colors",
  };

  const classesName = `
    ${sizeTable[size]} 
    ${
      disabled
        ? "text-gray-400 bg-gray-200 cursor-not-allowed"
        : styleTable[styleType]
    }
    rounded transition-colors
    ${className}
  `;

  const handleClick = (e) => {
    if (!disabled && onClick) {
      onClick(e);
    }
  };

  return (
    <button
      disabled={disabled}
      onClick={handleClick}
      type={type}
      {...props}
      className={classesName}
    >
      {children}
    </button>
  );
};

export default Button;
