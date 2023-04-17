import { Container } from "./style";

const Tag = ({ children }) => {
  return <Container href={`/tagged/TODO`}>{children}</Container>;
};

export default Tag;
