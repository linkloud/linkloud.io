import SocialButtonContainer from "./SocialButtonContainer";
import Modal from "../modal";

import { Logo } from "@/static/svg";

const AuthLoginModal = ({ isOpened, onCloseLoginModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseLoginModal}>
      <h1 className="h-full flex flex-col justify-center items-center md:static md:mt-auto mx-auto text-2xl font-semibold">
        <Logo className="w-[100px] h-[100px] my-7 md:hidden" />
        로그인
      </h1>
      <div className="flex flex-col items-center md:justify-between w-full md:w-96 h-full">
        <h2 className="hidden">소셜 계정으로 시작하기</h2>
        <SocialButtonContainer />
      </div>
    </Modal>
  );
};

export default AuthLoginModal;
