import { useNavigate } from "react-router-dom";

import useAuthStore from "@/stores/useAuthStore";

import ActionMenu from "../action-menu/ActionMenu";
import ActionMenuItem from "../action-menu/ActionMenuItem";

const HeaderActionMenu = ({ nickname, handleMouseLeave }) => {
  const navigate = useNavigate();
  const { logout } = useAuthStore();

  const handleLogout = async () => {
    await logout();
    navigate("/");
  };

  return (
    <ActionMenu onMouseLeave={handleMouseLeave}>
      {/* <ActionMenuItem to={"/profile"}>내 정보</ActionMenuItem> */}
      <ActionMenuItem onClick={handleLogout}>로그아웃</ActionMenuItem>
    </ActionMenu>
  );
};

export default HeaderActionMenu;
