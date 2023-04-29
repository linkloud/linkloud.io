import { Link } from "react-router-dom";

const AnchorBottomLine = ({ children, isActive = false, ...props }) => {
  const className = `${
    isActive ? "font-semibold border-b-2 border-primary-600" : ""
  } py-2 px-3 cursor-pointer`;

  return (
    <Link className={className} {...props}>
      {children}
    </Link>
  );
};

export default AnchorBottomLine;
