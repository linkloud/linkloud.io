import { Container, Input } from "./style";
import { SearchIcon } from "@/static/svg";
import SearchOptionPopup from "../searchOptionPopup";

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
      <SearchOptionPopup></SearchOptionPopup>
    </Container>
  );
};

export default Search;
