import { useState } from "react";

import WriteProgressContainer from "../WriteProgressContainer";
import WriteFormContainer from "../WriteFormContainer";

const WriteContainer = () => {
  // 작성 단계
  const [step, setStep] = useState(1);

  // 작성 폼 정보
  // TODO
  const [formData, setFormData] = useState({
    title: "",
    url: "",
    description: "",
    tags: [],
  });

  const nextStep = () => {
    setStep(step + 1);
  };

  const prevStep = () => {
    setStep(step - 1);
  };

  const handleInputData = (input) => (e) => {
    const { value } = e.target;

    // form 정보 이전 상태를 가져온 후 새 값을 추가한 객체로 set
    setFormData((prevState) => ({
      ...prevState,
      [input]: value,
    }));
  };

  return (
    <section className="w-full max-w-3xl mt-10">
      <h1 className="hidden">링크 아티클을 등록</h1>
      <WriteProgressContainer step={step} />
      <WriteFormContainer
        step={step}
        nextStep={nextStep}
        prevStep={prevStep}
        handleInputData={handleInputData}
      />
    </section>
  );
};

export default WriteContainer;
