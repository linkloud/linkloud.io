const DefaultButton = ({
  children,
  size = "md",
  styleType = "fill",
  ...props
}) => {
  const sizeTable = {
    xs: "px-2 h-6 text-sm",
    sm: "px-4 h-8 text-sm",
    md: "px-5 h-10",
    lg: "px-7 h-12",
  };

  const styleTable = {
    subtle: "text-neutral-800 hover:text-primary-600",
    lined:
      "rounded border border-neutral-300 bg-white hover:border-primary-500 hover:text-primary-500",
    fill: "rounded text-white bg-primary-600 hover:bg-primary-500 transition-colors",
  };

  const className = `${styleTable[styleType]} ${sizeTable[size]}`;

  return (
    <button {...props} type="button" className={className}>
      {children}
    </button>
  );
};

export default DefaultButton;
