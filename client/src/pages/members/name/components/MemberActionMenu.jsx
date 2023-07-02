import ActionMenu from "@/common/components/action-menu/ActionMenu";
import ActionMenuItem from "@/common/components/action-menu/ActionMenuItem";

const MemberActionMenu = ({ handleMouseLeave }) => {
  return (
    <ActionMenu onMouseLeave={handleMouseLeave}>
      <ActionMenuItem>이름 수정</ActionMenuItem>
      <ActionMenuItem>회원 탈퇴</ActionMenuItem>
    </ActionMenu>
  );
};

export default MemberActionMenu;
