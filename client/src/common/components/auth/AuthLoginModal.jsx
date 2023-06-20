import GoogleLoginButton from "./GoogleLoginButton";
import Modal from "../modal";

import { Logo } from "@/static/svg";

const AuthLoginModal = ({ isOpened, onCloseLoginModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseLoginModal}>
      <div className="w-44 sm:w-72 md:w-96 mx-auto">
        <h1 className="h-full mx-auto flex flex-col justify-center items-center ">
          <Logo className="block w-24 h-24" />
          <span className="text-2xl font-medium">Linkloud</span>
        </h1>

        <div className="mt-4 flex flex-col items-center md:justify-between w-full">
          <h2 className="sr-only">소셜 계정으로 시작하기</h2>
          <div className="flex flex-col w-full mt-auto">
            <GoogleLoginButton onCloseLoginModal={onCloseLoginModal} />
          </div>
        </div>
      </div>
    </Modal>
  );
};

export default AuthLoginModal;
