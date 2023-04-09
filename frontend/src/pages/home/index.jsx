import { Main, SearchContainer } from "./style";
import Banner from "@/components/banner";
import Search from "@/components/search";

const Home = () => {
  return (
    <Main>
      <Banner></Banner>
      <SearchContainer>
        <Search size="xl" styleType="default"></Search>
      </SearchContainer>
    </Main>
  );
};

export default Home;
