import { AnchorDefault } from "./style";

const Anchor = ({ children, ...props }) => {
  return <AnchorDefault {...props}>{children}</AnchorDefault>;
};

export default Anchor;
