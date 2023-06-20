import { Link } from "react-router-dom";

const AnchorSelectable = ({ children, isSelected = false, ...props }) => {
  const className = `${
    isSelected ? "font-semibold border-b-2 border-primary-600" : ""
  } py-2 px-3 cursor-pointer`;

  return (
    <Link className={className} {...props}>
      {children}
    </Link>
  );
};

export default AnchorSelectable;
