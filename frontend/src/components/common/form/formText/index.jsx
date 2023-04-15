import { Container } from "./style";

const FormText = ({ labelText, styleType = "default" }) => {
  return (
    <Container styleType={styleType}>
      <input required type="text" id={labelText} placeholder={labelText} />
      <label htmlFor={labelText}>{labelText}</label>
      <span></span>
    </Container>
  );
};

export default FormText;
