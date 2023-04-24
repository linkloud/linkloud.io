const Dimmed = ({ ...props }) => {
  return (
    <div
      {...props}
      className="fixed inset-0 z-40 cursor-pointer bg-black opacity-30"
    />
  );
};

export default Dimmed;
