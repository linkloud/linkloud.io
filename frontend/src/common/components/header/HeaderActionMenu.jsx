import ActionMenu from "../action-menu/ActionMenu";
import ActionMenuItem from "../action-menu/ActionMenuItem";

import useAuthStore from "@/stores/useAuthStore";
import useModalStore from "@/stores/useModalStore";

const HeaderActionMenu = ({ handleMouseLeave }) => {
  const { logout } = useAuthStore();
  // 태그 요청 모달
  const { openModal } = useModalStore();
  const handleOpenReqTagModal = () => openModal("requestTag");

  return (
    <ActionMenu onMouseLeave={handleMouseLeave}>
      <ActionMenuItem to="/members/name">내 정보</ActionMenuItem>
      <ActionMenuItem onClick={handleOpenReqTagModal}>태그요청</ActionMenuItem>
      <ActionMenuItem onClick={logout}>로그아웃</ActionMenuItem>
    </ActionMenu>
  );
};

export default HeaderActionMenu;
