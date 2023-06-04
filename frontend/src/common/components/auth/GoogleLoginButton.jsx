import { useGoogleLogin } from "@react-oauth/google";
import { toast } from "react-toastify";

import useAuthStore from "@/stores/useAuthStore";

import { GoogleIcon } from "@/static/svg";

const GoogleLoginButton = ({ onCloseLoginModal }) => {
  const { socialLogin } = useAuthStore();

  const login = useGoogleLogin({
    onSuccess: async (codeResponse) => {
      try {
        await socialLogin("google", codeResponse.code);
      } catch (e) {
        toast.error("로그인에 실패했습니다. 잠시후에 다시 시도해주세요");
      } finally {
        onCloseLoginModal();
      }
    },
    flow: "auth-code",
  });

  return (
    <a
      onClick={() => login()}
      role="button"
      className="flex items-center justify-center py-3 px-5 border border-gray-300 rounded w-full cursor-pointer transition hover:bg-zinc-50"
    >
      <GoogleIcon className="mr-2" />
      <span>구글로 시작하기</span>
    </a>
  );
};

export default GoogleLoginButton;
