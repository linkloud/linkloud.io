import { useGoogleLogin } from "@react-oauth/google";

import useAuthStore from "@/stores/useAuthStore";
import useModalStore from "@/stores/useModalStore";

import { GoogleIcon } from "@/static/svg";

const GoogleLoginButton = () => {
  const { socialLogin } = useAuthStore();
  const { closeModal } = useModalStore();

  const login = useGoogleLogin({
    onSuccess: async (codeResponse) => {
      await socialLogin("google", codeResponse.code);
      closeModal("login");
    },
    flow: "auth-code",
  });

  return (
    <a
      onClick={() => login()}
      className="flex items-center justify-center py-3 px-5 border border-gray-300 rounded  w-full cursor-pointer transition hover:bg-zinc-50"
    >
      <GoogleIcon className="mr-2" />
      <span>구글로 시작하기</span>
    </a>
  );
};

export default GoogleLoginButton;
