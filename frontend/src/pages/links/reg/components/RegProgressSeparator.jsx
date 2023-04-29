const RegProgressSepoarator = ({ isOn = false }) => {
  const barColor = isOn ? "bg-gray-500" : "bg-gray-300";
  const barClass = `${barColor} absolute top-1/2 translate-y-[-50%] w-full h-[2px] transition-all duration-300`;

  return (
    <div className="relative h-10 w-40">
      <div className={barClass} />
    </div>
  );
};

export default RegProgressSepoarator;
