const WriteProgressSepoaratorBar = ({ isComplete = false }) => {
  const sepoaratorBarColor = isComplete ? "bg-gray-500" : "bg-gray-300";
  const sepoaratorBarClass = `${sepoaratorBarColor} absolute top-1/2 translate-y-[-50%] w-full h-[2px] transition-all duration-300`;

  return (
    <div className="relative h-10 w-40">
      <div className={sepoaratorBarClass} />
    </div>
  );
};

export default WriteProgressSepoaratorBar;
