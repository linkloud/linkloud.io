import { useState } from "react";

import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";

import useModalStore from "@/stores/useModalStore";

const LinksRegPage = () => {
  // 작성 폼 정보
  // TODO
  const [formData, setFormData] = useState({
    title: "",
    url: "",
    description: "",
    tags: [],
  });

  // 태그 요청 모달
  const { openModal } = useModalStore();
  const handleOpenArticleRegConfirmModal = () => openModal("articleRegConfirm");

  const handleInputData = (input) => (e) => {
    const { value } = e.target;

    // form 정보 이전 상태를 가져온 후 새 값을 추가한 객체로 set
    setFormData((prevState) => ({
      ...prevState,
      [input]: value,
    }));
  };

  return (
    <section className="mt-20 px-6 w-full max-w-3xl">
      <h1 className="sr-only">link article register section</h1>
      <p className="text-2xl font-semibold">
        게시하려는 링크의 이름과 주소를 작성해주세요.
      </p>
      <InputText labelText="이름" className="mt-8" />
      <InputText labelText="주소 URL" className="mt-8" />
      <p className="my-8 text-2xl font-semibold">
        간단한 한 줄 설명을 작성해주세요.
      </p>
      <InputText labelText="설명" className="mt-8" />
      <div className="mt-8 flex justify-end w-full">
        <Button
          onClick={handleOpenArticleRegConfirmModal}
          size="lg"
          styleType="fill"
        >
          계속
        </Button>
      </div>
    </section>
  );
};

export default LinksRegPage;
