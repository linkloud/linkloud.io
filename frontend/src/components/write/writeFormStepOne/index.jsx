import InputText from "@/components/common/input/InputText";

const WriteFormStepOne = () => {
  return (
    <div className="flex flex-col gap-8">
      <InputText labelText="이름"></InputText>
      <InputText labelText="주소(url)"></InputText>
    </div>
  );
};

export default WriteFormStepOne;
