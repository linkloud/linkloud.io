const ActionMenu = ({ children, ...props }) => {
  return (
    <ul
      {...props}
      className="absolute mt-4 -right-10 w-[200px] py-3 bg-white border border-gray-100 rounded shadow-md z-30"
    >
      {children}
    </ul>
  );
};

export default ActionMenu;
