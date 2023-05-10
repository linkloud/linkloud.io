const Button = ({
  children,
  size = "md",
  styleType = "fill",
  className,
  ...props
}) => {
  const sizeTable = {
    xs: "px-2 h-6 text-sm",
    sm: "px-4 h-8 text-sm",
    md: "px-5 h-10",
    lg: "px-7 h-12",
  };

  const styleTable = {
    subtle: "rounded hover:bg-gray-100 transition-colors",
    lined:
      "rounded border border-neutral-300 bg-white hover:border-primary-500",
    fill: "rounded text-white bg-primary-600 hover:bg-primary-500 transition-colors",
  };

  const styleClassName = `${styleTable[styleType]} ${sizeTable[size]}`;

  return (
    <button
      {...props}
      type="button"
      className={`${styleClassName} ${className ? className : ""}`}
    >
      {children}
    </button>
  );
};

export default Button;
