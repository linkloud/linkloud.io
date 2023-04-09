import { Container, Input } from "./style";
import { SearchIcon } from "@/static/svg";

const Search = ({ size = "md", styleType = "default" }) => {
  return (
    <Container>
      <SearchIcon />
      <form action="search" method="get">
        <label htmlFor="search">검색</label>
        <Input
          id="search"
          size={size}
          styleType={styleType}
          placeholder="검색하기"
          type="search"
        ></Input>
      </form>
    </Container>
  );
};

export default Search;
