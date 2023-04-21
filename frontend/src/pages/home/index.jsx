import { SearchContainer, ArticleOrder, ArticleCardList } from "./style";
import Banner from "@/components/banner";
import Search from "@/components/search";
import ArticleCard from "@/components/article/ArticleCard";
import TagContainer from "@/components/tag/TagItemList";
import AnchorBottomLine from "@/components/common/anchor/AnchorBottomLine";

const HomePage = () => {
  const fakeArticleList = [
    {
      title: "이름1",
      description: "설명",
    },
    {
      title: "이름2",
      description: "설명",
    },
    {
      title: "이름3",
      description: "설명",
    },
    {
      title: "이름4",
      description: "설명",
    },
    {
      title: "이름5",
      description: "설명",
    },
    {
      title: "이름6",
      description: "설명",
    },
    {
      title: "이름7",
      description: "설명",
    },
    {
      title: "이름8",
      description: "설명",
    },
    {
      title: "이름9",
      description: "설명",
    },
    {
      title: "이름10",
      description: "설명",
    },
  ];

  const orderList = [
    {
      id: 1,
      name: "최신순",
      isSelected: true,
    },
    {
      id: 2,
      name: "인기순",
      isSelected: false,
    },
    {
      id: 3,
      name: "이주의 링크",
      isSelected: false,
    },
    {
      id: 4,
      name: "이달의 링크",
      isSelected: false,
    },
  ];

  return (
    <>
      <Banner />
      <SearchContainer>
        <Search size="xl" styleType="default"></Search>
      </SearchContainer>
      <TagContainer></TagContainer>
      <ArticleCardList>
        <h1>링크 아티클 섹션</h1>
        <ArticleOrder>
          <ul>
            {orderList.map((o) => (
              <li key={o.id}>
                <AnchorBottomLine isActive={o.isSelected}>
                  {o.name}
                </AnchorBottomLine>
              </li>
            ))}
          </ul>
        </ArticleOrder>
        {fakeArticleList.map((a) => (
          <ArticleCard article={a} key={a.title}></ArticleCard>
        ))}
      </ArticleCardList>
    </>
  );
};

export default HomePage;
