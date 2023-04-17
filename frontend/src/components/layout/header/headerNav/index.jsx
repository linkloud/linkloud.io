import { GnbUl, Nav } from "./style";
import Button from "@/components/common/button";
import LoginModalContainer from "@/components/auth/loginModalContainer";

import { useModalActions, useLoginModalState } from "@/stores/useModalStore";

const HeaderNav = ({ role = "GUEST" }) => {
  // 로그인 모달
  const isLoginModalOpened = useLoginModalState();
  const { setOpen, setClose } = useModalActions();

  const handleOpenLoginModal = () => {
    setOpen("login");
  };

  const handleCloseLoginModal = () => {
    setClose("login");
  };
  // 로그인 모달 끝

  return (
    <Nav>
      <h1>네비게이션</h1>
      <GnbUl>
        <li>
          <Button
            size="md"
            styleType="default"
            aria-haspopup="dialog"
            onClick={console.log("공지사항")}
          >
            공지사항
          </Button>
        </li>
        {role === "ADMIN" && (
          <li>
            <Button
              size="md"
              styleType="default"
              aria-haspopup="dialog"
              onClick={console.log("태그등록")}
            >
              태그 등록
            </Button>
          </li>
        )}
        <li>
          <Button
            size="md"
            styleType="default"
            aria-haspopup="dialog"
            onClick={console.log("링크 등록")}
          >
            링크 등록
          </Button>
        </li>
        {role === "GUEST" && (
          <li>
            <Button
              size="md"
              styleType="fill"
              aria-haspopup="dialog"
              onClick={handleOpenLoginModal}
            >
              로그인
            </Button>
            <LoginModalContainer
              isOpened={isLoginModalOpened}
              onCloseLoginModal={handleCloseLoginModal}
            ></LoginModalContainer>
          </li>
        )}
      </GnbUl>
    </Nav>
  );
};

export default HeaderNav;
