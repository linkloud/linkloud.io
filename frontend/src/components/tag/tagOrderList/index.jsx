import { TagOrder } from "./style";
import AnchorBottomLine from "@/components/common/anchor/anchorBottomLine";

const TagOrderList = () => {
  const orderList = [
    {
      id: 1,
      name: "인기순",
      isSelected: true,
    },
    {
      id: 2,
      name: "최신순",
      isSelected: false,
    },
    {
      id: 3,
      name: "이름순",
      isSelected: false,
    },
  ];

  const t = (e) => {
    console.log(e.target.key);
  };

  return (
    <TagOrder>
      {orderList.map((o) => (
        <li key={o.id} onClick={t}>
          <AnchorBottomLine isActive={o.isSelected}>{o.name}</AnchorBottomLine>
        </li>
      ))}
    </TagOrder>
  );
};

export default TagOrderList;
