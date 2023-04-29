import GoogleLoginButton from "./GoogleLoginButton";

const SocialButtonContainer = () => {
  return (
    <div className="flex flex-col w-full mt-auto">
      <p className="mx-auto my-5">소셜 계정으로 간편하게 시작하세요</p>
      <GoogleLoginButton />
    </div>
  );
};

export default SocialButtonContainer;
