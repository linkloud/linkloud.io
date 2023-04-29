import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";

const MultiStepForm = ({ step, nextStep, prevStep, handleInputData }) => {
  switch (step) {
    case 1:
      return (
        <div className="flex flex-col gap-10">
          <h2 className="text-xl">
            게시하려는 링크의 이름과 주소를 작성해주세요.
          </h2>
          <div className="flex flex-col gap-8">
            <InputText labelText="이름"></InputText>
            <InputText labelText="주소(url)"></InputText>
          </div>
          <div className="flex justify-end">
            <Button
              size="md"
              styleType="fill"
              onClick={nextStep}
              aria-label="다음 입력으로"
            >
              다음
            </Button>
          </div>
        </div>
      );
    case 2:
      return (
        <div className="flex flex-col gap-10">
          <h2 className="text-xl">간단한 한 줄 설명을 작성해주세요.</h2>
          <InputText labelText="설명"></InputText>
          <div className="flex justify-between">
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
          </div>
        </div>
      );
    case 3:
      return (
        <div className="flex flex-col gap-10">
          <h2 className="text-xl">태그를 넣으면 찾기 쉬워질거에요.(선택)</h2>
          <span>TODO</span>
          <div className="flex justify-between">
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
          </div>
        </div>
      );
  }
};

export default MultiStepForm;