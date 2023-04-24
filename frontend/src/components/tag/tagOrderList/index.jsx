import AnchorBottomLine from "@/components/common/anchor/AnchorBottomLine";

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

  const t = (e) => {};

  return (
    <ul className="flex mt-7 mb-4">
      {orderList.map((o) => (
        <li key={o.id} onClick={t} className="mr-1">
          <AnchorBottomLine isActive={o.isSelected}>{o.name}</AnchorBottomLine>
        </li>
      ))}
    </ul>
  );
};

export default TagOrderList;
