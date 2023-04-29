import { Link, useNavigate } from "react-router-dom";

import Button from "../button";
import UserProfile from "../user/UserProfile";
import AuthLoginModal from "../auth/AuthLoginModal";
import { LogoLabel } from "@/static/svg";

import useUserStore from "@/stores/useUserStore";
import { useModalActions, useLoginModalState } from "@/stores/useModalStore";

const Header = () => {
  const navigate = useNavigate();

  const { userRole, name, profileImage } = useUserStore((state) => ({
    name: state.name,
    userRole: state.role,
    profileImage: state.profileImage,
  }));

  // 로그인 모달
  const isLoginModalOpened = useLoginModalState();
  const { setOpen, setClose } = useModalActions();

  const handleOpenLoginModal = () => setOpen("login");

  const handleCloseLoginModal = () => setClose("login");
  // 로그인 모달 끝

  const handleRegisterTag = () => {
    if (!userRole || userRole === "guest") {
      setOpen("login");
      return;
    }
    navigate("/links/reg");
  };

  return (
    <header className="sticky top-0 bg-white border-b-gray-300 z-50">
      <div className="mx-auto px-4 flex justify-between items-center h-20 max-w-6xl">
        <h1>
          <span className="hidden">linkloud</span>
          <Link to="/" aria-label="linkloud home">
            <LogoLabel />
          </Link>
        </h1>
        <nav>
          <h1 className="hidden">navigation</h1>
          <ul className="flex items-center">
            <li className="mr-2">
              <Button
                size="md"
                styleType="subtle"
                aria-haspopup="dialog"
                onClick={console.log("공지사항")}
              >
                공지사항
              </Button>
            </li>
            {userRole === "admin" && (
              <li className="mr-2">
                <Button
                  size="md"
                  styleType="inverted"
                  aria-haspopup="dialog"
                  onClick={console.log("태그등록")}
                >
                  태그 등록
                </Button>
              </li>
            )}
            {userRole !== "guest" && (
              <li className="mr-2">
                <Button
                  size="md"
                  styleType="default"
                  aria-haspopup="dialog"
                  onClick={handleRegisterTag}
                >
                  링크 등록
                </Button>
              </li>
            )}

            {userRole === "guest" && (
              <li className="mr-2">
                <Button
                  size="md"
                  styleType="fill"
                  aria-haspopup="dialog"
                  onClick={handleOpenLoginModal}
                >
                  로그인
                </Button>
                <AuthLoginModal
                  isOpened={isLoginModalOpened}
                  onCloseLoginModal={handleCloseLoginModal}
                ></AuthLoginModal>
              </li>
            )}
            {userRole !== "guest" && (
              <li className="px-2">
                <UserProfile name={name} profileImage={profileImage} />
              </li>
            )}
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;