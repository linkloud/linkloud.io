import { Title, SocialLoginContainer } from "./style";
import SocialList from "@/components/auth/SocialList";
import Modal from "@/components/common/modal";

const LoginModalContainer = ({ isOpened, onCloseLoginModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseLoginModal}>
      <Title>로그인</Title>
      <SocialLoginContainer>
        <h2>소셜 계정으로 시작하기</h2>
        <p>소셜 계정으로 간편하게 시작하세요</p>
        <SocialList />
      </SocialLoginContainer>
    </Modal>
  );
};

export default LoginModalContainer;
