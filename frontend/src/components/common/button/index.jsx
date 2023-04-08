import { ButtonDefault } from "./style";

const Button = ({ children, ...props }) => {
  return (
    <ButtonDefault {...props} type="button">
      {children}
    </ButtonDefault>
  );
};

export default Button;
