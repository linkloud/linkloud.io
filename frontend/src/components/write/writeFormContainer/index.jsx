import { Container } from "./style";
import WriteFormStepOne from "../writeFormStepOne";
import WriteFormStepTwo from "../writeFormStepTwo";
import WriteFormStepThree from "../writeFormStepThree";
import Button from "@/components/common/button";
import { FlexRow } from "@/styles";

const WriteFormContainer = ({ step, nextStep, prevStep, handleInputData }) => {
  switch (step) {
    case 1:
      return (
        <Container>
          <p>게시하려는 링크의 이름과 주소를 작성해주세요.</p>
          <WriteFormStepOne></WriteFormStepOne>
          <FlexRow css={{ justifyContent: "flex-end;" }}>
            <Button
              size="md"
              styleType="fill"
              onClick={nextStep}
              aria-label="다음 입력으로"
            >
              다음
            </Button>
          </FlexRow>
        </Container>
      );
    case 2:
      return (
        <Container>
          <p>간단한 한 줄 설명을 작성해주세요.</p>
          <WriteFormStepTwo></WriteFormStepTwo>
          <FlexRow css={{ justifyContent: "space-between" }}>
            <Button
              size="md"
              styleType="fill"
              onClick={prevStep}
              aria-label="이전 입력으로"
            >
              이전
            </Button>
            <Button
              size="md"
              styleType="fill"
              onClick={nextStep}
              aria-label="다음 입력으로"
            >
              다음
            </Button>
          </FlexRow>
        </Container>
      );
    case 3:
      return (
        <Container>
          <p>태그를 넣으면 찾기 쉬워질거에요.(선택)</p>
          <WriteFormStepThree></WriteFormStepThree>
          <FlexRow css={{ justifyContent: "space-between" }}>
            <Button
              size="md"
              styleType="fill"
              onClick={prevStep}
              aria-label="이전 입력으로"
            >
              이전
            </Button>
            <Button size="md" styleType="fill" onClick={nextStep}>
              등록
            </Button>
          </FlexRow>
        </Container>
      );
  }
};

export default WriteFormContainer;
