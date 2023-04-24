import DefaultButton from "@/components/common/button/DefaultButton";
import LoginModalContainer from "@/container/LoginModalContainer";

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
    <nav>
      <h1 className="hidden">navigation</h1>
      <ul className="flex items-center">
        <li className="mr-2">
          <DefaultButton
            size="md"
            styleType="default"
            aria-haspopup="dialog"
            onClick={console.log("공지사항")}
          >
            공지사항
          </DefaultButton>
        </li>
        {role === "ADMIN" && (
          <li className="mr-2">
            <DefaultButton
              size="md"
              styleType="inverted"
              aria-haspopup="dialog"
              onClick={console.log("태그등록")}
            >
              태그 등록
            </DefaultButton>
          </li>
        )}
        <li className="mr-2">
          <DefaultButton
            size="md"
            styleType="default"
            aria-haspopup="dialog"
            onClick={console.log("링크 등록")}
          >
            링크 등록
          </DefaultButton>
        </li>
        {role === "GUEST" && (
          <li className="mr-2">
            <DefaultButton
              size="md"
              styleType="fill"
              aria-haspopup="dialog"
              onClick={handleOpenLoginModal}
            >
              로그인
            </DefaultButton>
            <LoginModalContainer
              isOpened={isLoginModalOpened}
              onCloseLoginModal={handleCloseLoginModal}
            ></LoginModalContainer>
          </li>
        )}
      </ul>
    </nav>
  );
};

export default HeaderNav;
