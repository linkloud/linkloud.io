import CountUp from "react-countup";

const Counter = ({ number, ...props }) => {
  return <CountUp duration={5} end={number} separator="," {...props} />;
};

export default Counter;
