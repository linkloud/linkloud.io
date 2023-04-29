import { Link } from "react-router-dom";

const Anchor = ({ children, className = "", ...props }) => {
  return (
    <Link
      className={
        className +
        " cursor-pointer hover:text-blue-500 hover:fill-blue-500 hover:stroke-blue-500 hover:underline"
      }
      {...props}
    >
      {children}
    </Link>
  );
};

export default Anchor;
