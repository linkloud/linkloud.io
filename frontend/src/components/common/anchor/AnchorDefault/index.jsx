const Anchor = ({ children, className = "", ...props }) => {
  return (
    <a
      className={
        className +
        " cursor-pointer hover:text-blue-500 hover:fill-blue-500 hover:stroke-blue-500 hover:underline"
      }
      {...props}
    >
      {children}
    </a>
  );
};

export default Anchor;
