import { Container } from "./style";

const LabelCircle = ({ children, ...props }) => {
  return <Container {...props}>{children}</Container>;
};

export default LabelCircle;
