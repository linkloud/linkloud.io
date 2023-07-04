import CountUp, { CountUpProps } from "react-countup";

export const Counter = ({ end, duration = 5, ...props }: CountUpProps) => {
  return <CountUp duration={duration} end={end} separator="," {...props} />;
};
