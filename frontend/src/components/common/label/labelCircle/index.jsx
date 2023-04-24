const LabelCircle = ({
  size = "md",
  styleType = "disabled",
  children,
  ...props
}) => {
  const sizeTable = {
    xs: "w-4 h-4 text-xs",
    sm: "w-6 h-6 text-sm",
    md: "w-8 h-8",
    lg: "w-10 h-10 text-lg",
  };

  const styleTable = {
    primary: "text-neutral-50 bg-primary-600",
    success: "text-neutral-50",
    warn: "text-neutral-50 bg-red-600",
    disabled: "text-neutral-50 bg-zinc-400",
  };

  const className = `${styleTable[styleType]} ${sizeTable[size]}`;

  return (
    <span
      {...props}
      className={`${className} inline-flex justify-center items-center leading-3 rounded-full font-semibold`}
    >
      {children}
    </span>
  );
};

export default LabelCircle;
