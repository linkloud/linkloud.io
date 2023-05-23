import SocialButtonContainer from "./SocialButtonContainer";
import Modal from "../modal";

import { Logo } from "@/static/svg";

const AuthLoginModal = ({ isOpened, onCloseLoginModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseLoginModal}>
      <h1 className="h-full flex flex-col justify-center items-center mx-auto ">
        <Logo className="block w-24 h-24" />
        <span className="text-2xl font-medium">Linkloud</span>
      </h1>
      <p className="py-5 text-lg text-gray-400">오신 것을 환영합니다.</p>
      <div className="flex flex-col items-center md:justify-between w-full">
        <h2 className="sr-only">소셜 계정으로 시작하기</h2>
        <SocialButtonContainer />
      </div>
    </Modal>
  );
};

export default AuthLoginModal;
