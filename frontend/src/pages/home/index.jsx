import { SearchContainer, ArticleSection } from "./style";
import Banner from "@/components/banner";
import Search from "@/components/search/searchContainer";
import ArticleCard from "@/components/article/articleCard";
import TagContainer from "@/components/tag/tagContainer";

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

  return (
    <>
      <Banner />
      <SearchContainer>
        <Search size="xl" styleType="default"></Search>
      </SearchContainer>
      <TagContainer></TagContainer>
      <ArticleSection>
        <h1>링크 아티클 섹션</h1>
        {fakeArticleList.map((a) => (
          <ArticleCard article={a} key={a.title}></ArticleCard>
        ))}
      </ArticleSection>
    </>
  );
};

export default HomePage;
