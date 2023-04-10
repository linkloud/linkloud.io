import { useGoogleLogin } from "@react-oauth/google";
import { GoogleLoginBtn } from "./style";
import { GoogleIcon } from "@/static/svg";

const GoogleLoginButton = () => {
  const login = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    flow: "auth-code",
  });

  return (
    <GoogleLoginBtn onClick={() => login()}>
      <GoogleIcon />
      <span>구글로 시작하기</span>
    </GoogleLoginBtn>
  );
};

export default GoogleLoginButton;
