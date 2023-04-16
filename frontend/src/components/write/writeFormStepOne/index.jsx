import InputText from "@/components/common/input/inputText";
import { FlexColumn } from "@/styles";

const WriteFormStepOne = () => {
  return (
    <FlexColumn css={{ gap: "24px" }}>
      <InputText labelText="이름"></InputText>
      <InputText labelText="주소(url)"></InputText>
    </FlexColumn>
  );
};

export default WriteFormStepOne;
