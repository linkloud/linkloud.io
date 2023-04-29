import { memo } from "react";
import { useNavigate } from "react-router-dom";

import DefaultButton from "@/components/common/button/DefaultButton";
import LoginModalContainer from "@/container/LoginModalContainer";
import UserProfileIcon from "@/components/user/UserProfileIcon";

import useUserStore from "@/stores/useUserStore";
import { useModalActions, useLoginModalState } from "@/stores/useModalStore";

const HeaderNav = memo(() => {
  const navigate = useNavigate();

  const { userRole, name, profileImage } = useUserStore((state) => ({
    name: state.name,
    userRole: state.role,
    profileImage: state.profileImage,
  }));

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

  const handleRegisterTag = () => {
    if (!userRole || userRole === "guest") {
      setOpen("login");
      return;
    }
    navigate("/links/reg");
  };

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
        {userRole === "admin" && (
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
        {userRole !== "guest" && (
          <li className="mr-2">
            <DefaultButton
              size="md"
              styleType="default"
              aria-haspopup="dialog"
              onClick={handleRegisterTag}
            >
              링크 등록
            </DefaultButton>
          </li>
        )}

        {userRole === "guest" && (
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
        {userRole !== "guest" && (
          <li className="px-2">
            <UserProfileIcon name={name} profileImage={profileImage} />
          </li>
        )}
      </ul>
    </nav>
  );
});

export default HeaderNav;
