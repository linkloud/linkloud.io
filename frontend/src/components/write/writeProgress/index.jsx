import { Container } from "./style";
import LabelCircle from "@/components/common/label/labelCircle";
import { CheckMarkIcon } from "@/static/svg";

const WriteProgress = ({ state, stepNum, content }) => {
  let labelCircleStyle;

  switch (state) {
    case "disabled":
      labelCircleStyle = "disabled";
      break;
    case "inProgress":
    case "complete":
      labelCircleStyle = "primary";
      break;
  }

  return (
    <Container styleType={state}>
      <LabelCircle size="lg" styleType={labelCircleStyle}>
        {state === "complete" ? <CheckMarkIcon /> : stepNum}
      </LabelCircle>
      <span>{content}</span>
    </Container>
  );
};

export default WriteProgress;
