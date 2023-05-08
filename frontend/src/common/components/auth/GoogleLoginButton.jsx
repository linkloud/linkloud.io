import { useGoogleLogin } from "@react-oauth/google";

import { socialLogin } from "@/service/api";
import { GoogleIcon } from "@/static/svg";

const GoogleLoginButton = () => {
  const login = useGoogleLogin({
    onSuccess: async (codeResponse) => {
      await socialLogin("google", codeResponse.code);
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
