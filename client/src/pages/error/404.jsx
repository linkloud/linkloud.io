import { useNavigate } from "react-router-dom";

import Button from "@/common/components/button";

const NotFoundPage = () => {
  const navigate = useNavigate();

  const handleClickBackBtn = () => {
    navigate(-1);
  };

  const handleClickHomeBtn = () => {
    navigate("/");
  };

  return (
    <main className="w-full h-full flex flex-col justify-center items-center">
      <h1 className=" text-2xl">페이지를 찾을 수 없습니다.</h1>
      <div className="mt-6">
        <Button
          onClick={handleClickBackBtn}
          size="md"
          styleType="lined"
          className="mr-3"
        >
          이전으로
        </Button>
        <Button onClick={handleClickHomeBtn} size="md" styleType="lined">
          linkloud 홈
        </Button>
      </div>
    </main>
  );
};

export default NotFoundPage;
