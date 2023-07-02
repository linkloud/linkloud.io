const Dimmed = ({ ...props }) => {
  return (
    <div
      {...props}
      className="fixed inset-0 w-full h-full cursor-pointer bg-black opacity-30 z-[999]"
    />
  );
};

export default Dimmed;
