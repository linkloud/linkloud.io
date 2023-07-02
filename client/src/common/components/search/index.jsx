import { useState } from "react";

import { SearchIcon } from "@/static/svg";

const Search = ({ onSearch }) => {
  const [isInputFocused, setIsInputFocused] = useState(false);

  const handleKeyDown = (e) => {
    if (e.key !== "Enter") return;
    e.preventDefault();
    onSearch(e.target.value);
  };

  return (
    <div className="relative flex items-center w-full py-3 px-4 pl-10 bg-white rounded-lg shadow-lg">
      <label htmlFor="search" className="sr-only">
        검색
      </label>
      <SearchIcon className="absolute left-4 w-4 h-4 stroke-gray-600" />
      <div className="flex w-full flex-wrap">
        <input
          id="search"
          placeholder="검색하기"
          type="search"
          autoComplete="off"
          onFocus={() => setIsInputFocused(true)}
          onBlur={() => setIsInputFocused(false)}
          onKeyDown={handleKeyDown}
          className="ml-1 min-w-full inline-block leading-7 outline-none"
        />
      </div>

      {isInputFocused && (
        <div
          className={
            "p-4 absolute top-14 left-0 w-full max-w-full bg-white border border-gray-50 rounded-md shadow-lg translate-y-[30%]"
          }
        >
          <p>
            [tag]{" "}
            <span className="ml-2 text-gray-400">태그와 함께 검색합니다</span>
          </p>
        </div>
      )}
    </div>
  );
};

export default Search;
