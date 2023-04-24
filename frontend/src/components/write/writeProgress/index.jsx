import LabelCircle from "@/components/common/label/LabelCircle";

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

  const spanClass = state === "disabled" ? "text-zinc-400" : "";

  return (
    <div className="flex items-center font-semibold text-lg">
      <LabelCircle size="lg" styleType={labelCircleStyle}>
        {state === "complete" ? (
          <CheckMarkIcon className="stroke-neutral-50" />
        ) : (
          stepNum
        )}
      </LabelCircle>
      <span className={`${spanClass} ml-2 transition-all duration-300}`}>
        {content}
      </span>
    </div>
  );
};

export default WriteProgress;
