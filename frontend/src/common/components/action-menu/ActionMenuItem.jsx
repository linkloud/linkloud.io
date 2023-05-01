import { Link } from "react-router-dom";

const ActionMenuItem = ({ children, to, ...props }) => {
  return (
    <li>
      <Link
        to={to}
        {...props}
        className="flex items-center px-3 py-2 hover:bg-gray-100 cursor-pointer"
      >
        {children}
      </Link>
    </li>
  );
};

export default ActionMenuItem;
