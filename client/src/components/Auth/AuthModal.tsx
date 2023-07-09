import GoogleLoginButton from "./GoogleLoginButton";
import { Modal, ModalProps } from "../Modal";

import { Logo } from "@/assets/svg";

export const AuthModal = ({ isOpened, onClose }: Omit<ModalProps, "name">) => {
  return (
    <Modal name="로그인" isOpened={isOpened} onClose={onClose}>
      <div className="flex flex-col h-full p-6 sm:w-96">
        <div className="flex flex-col justify-center items-center h-full">
          <Logo className="w-24 h-24" />
          <p className="text-xl font-medium text-gray-800">로그인</p>
        </div>

        <div className="mb-20 sm:mb-0 sm:pt-10">
          <GoogleLoginButton onLoginFinish={onClose} />
        </div>
      </div>
    </Modal>
  );
};
