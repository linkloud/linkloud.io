import RegProgress from "./RegProgress";
import RegProgressSepoarator from "./RegProgressSeparator";

const RegProgressContainer = ({ step }) => {
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
    <div className="w-full my-12 flex items-center justify-between">
      <RegProgress stepNum={1} content="기본정보" state={firstState} />
      <RegProgressSepoarator isOn={firstSeparatorComplete} />
      <RegProgress stepNum={2} content="설명" state={secondState}></RegProgress>
      <RegProgressSepoarator isOn={secondSeparatorComplete} />
      <RegProgress stepNum={3} content="태그" state={thirdState}></RegProgress>
    </div>
  );
};

export default RegProgressContainer;
