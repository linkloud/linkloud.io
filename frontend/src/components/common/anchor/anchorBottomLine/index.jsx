import { Container } from "./style";

const AnchorBottomLine = ({ children, isActive = false }) => {
  return <Container isActive={isActive}>{children}</Container>;
};

export default AnchorBottomLine;
