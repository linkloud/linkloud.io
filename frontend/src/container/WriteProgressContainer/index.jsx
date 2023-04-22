import { Container } from "./style";
import WriteProgress from "@/components/write/WriteProgress";
import WriteProgressSepoaratorBar from "@/components/write/WriteProgressSepoaratorBar";

const WriteProgressContainer = ({ step }) => {
  let firstState;
  let firstSeparatorComplete;
  let secondState;
  let secondSeparatorComplete;
  let thirdState;

  switch (step) {
    case 1:
      firstState = "inProgress";
      firstSeparatorComplete = false;
      secondState = "disabled";
      thirdState = "disabled";
      break;
    case 2:
      firstState = "complete";
      firstSeparatorComplete = true;
      secondState = "inProgress";
      thirdState = "disabled";
      break;
    case 3:
      firstState = "complete";
      firstSeparatorComplete = true;
      secondState = "complete";
      secondSeparatorComplete = true;
      thirdState = "inProgress";
      break;
  }

  return (
    <Container>
      <WriteProgress stepNum={1} content="기본정보" state={firstState} />
      <WriteProgressSepoaratorBar isComplete={firstSeparatorComplete} />
      <WriteProgress
        stepNum={2}
        content="설명"
        state={secondState}
      ></WriteProgress>
      <WriteProgressSepoaratorBar isComplete={secondSeparatorComplete} />
      <WriteProgress
        stepNum={3}
        content="태그"
        state={thirdState}
      ></WriteProgress>
    </Container>
  );
};

export default WriteProgressContainer;
