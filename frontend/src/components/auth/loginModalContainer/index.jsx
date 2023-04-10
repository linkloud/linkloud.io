import { Title, SocialLoginSection } from "./style";
import SocialList from "../socialList";
import Modal from "@/components/common/modal";
import { FlexRow } from "@/styles";
import { LogoLabel } from "@/static/svg";

const LoginModalContainer = ({ isOpened, onCloseLoginModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseLoginModal}>
      <Title>로그인</Title>
      <FlexRow css={{ justifyContent: "center" }}>
        <LogoLabel />
      </FlexRow>

      <SocialLoginSection>
        <h1>소셜 계정으로 시작하기</h1>
        <SocialList />
      </SocialLoginSection>
    </Modal>
  );
};

export default LoginModalContainer;
