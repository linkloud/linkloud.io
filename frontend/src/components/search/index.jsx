import { useState } from "react";
import SearchOptionPopup from "./SearchOptionPopup";

import { SearchIcon } from "@/static/svg";

const Search = () => {
  const [isInputFocused, setIsInputFocused] = useState(false);

  return (
    <div className="flex items-center relative w-full">
      <SearchIcon className="absolute left-4 w-4 h-4 stroke-gray-600" />
      <form action="search" method="get" className="w-full">
        <label htmlFor="search" className="hidden">
          검색
        </label>
        <input
          id="search"
          placeholder="검색하기"
          type="search"
          onFocus={() => setIsInputFocused(true)}
          onBlur={() => setIsInputFocused(false)}
          className="h-14 w-full leading-7 px-4 pl-10 outline-none rounded-lg shadow-lg"
        ></input>
      </form>
      <SearchOptionPopup isVisible={isInputFocused}></SearchOptionPopup>
    </div>
  );
};

export default Search;
