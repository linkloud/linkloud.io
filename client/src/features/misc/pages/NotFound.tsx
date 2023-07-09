import { useNavigate } from "react-router-dom";

import { Button } from "@/components/Button";

const NotFound = () => {
  const navigate = useNavigate();

  const handleClickBack = () => {
    navigate(-1);
  };

  const handleClickHome = () => {
    navigate("/");
  };

  return (
    <main className="w-full h-screen flex flex-col justify-center items-center">
      <h1 className="sr-only">linkloud</h1>
      <h2 className="text-2xl">페이지를 찾을 수 없습니다.</h2>
      <div className="flex gap-3 mt-6">
        <Button
          name="이전으로"
          styleName="outline-neutral"
          size="xl"
          className="w-36"
          onClick={handleClickBack}
        >
          이전으로
        </Button>
        <Button
          name="홈으로"
          styleName="outline-neutral"
          size="xl"
          className="w-36"
          onClick={handleClickHome}
        >
          linkloud 홈
        </Button>
      </div>
    </main>
  );
};

export default NotFound;
