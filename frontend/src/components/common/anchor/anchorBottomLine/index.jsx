const AnchorBottomLine = ({ children, isActive = false }) => {
  const className = `${
    isActive ? "font-semibold border-b-2 border-primary-600" : ""
  } py-2 px-3 cursor-pointer`;

  return <a className={className}>{children}</a>;
};

export default AnchorBottomLine;
