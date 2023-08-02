import { useState, KeyboardEvent, ChangeEvent } from "react";

import {
  useArticleSearchNavigation,
  useArticleSearchValidation,
} from "@/features/articles";

import { Portal } from "../Portal";

export interface SearchMenuProps {
  onSearch: (value: string) => void;
  onClose: () => void;
}

export const SearchMenu = ({ onSearch, onClose }: SearchMenuProps) => {
  const [enteredKeyword, setEnteredKeyword] = useState("");

  const { handleSearchNavigation } = useArticleSearchNavigation();
  const { searchValidationError, validateSearch } =
    useArticleSearchValidation();

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    setEnteredKeyword(value);
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== "Enter") return;

    e.preventDefault();

    if (!validateSearch(enteredKeyword)) return;
    handleSearchNavigation(enteredKeyword);
    onSearch(enteredKeyword);
  };

  return (
    <>
      <section className="fixed left-0 mt-4 h-60 w-full bg-white shadow-xl z-[5] animate-fade-down animate-once animate-duration-300 animate-ease-out">
        <div className="mx-auto w-full max-w-7xl px-6">
          <h1 className="sr-only">검색하기</h1>
          <div className="pt-8">
            <input
              id="search"
              placeholder="검색하기"
              autoFocus
              autoComplete="off"
              className="w-full text-xl font-medium outline-none"
              onChange={handleInputChange}
              onKeyDown={handleKeyDown}
            />
          </div>
          {searchValidationError && (
            <p className="text-warn-medium animate-shake animate-thrice animate-duration-100 animate-ease-out">
              {searchValidationError}
            </p>
          )}
          <p className="pt-2">
            <span className="font-medium">• [tag]</span>
            <span className="ml-2 text-neutral-600">
              검색 옵션에 태그를 추가할 수 있습니다.
            </span>
          </p>
        </div>
      </section>

      <Portal elementId="root">
        <div
          className="fixed inset-0 h-full w-full blur backdrop-blur-sm z-[5] animate-fade animate-once animate-duration-300 animate-ease-linear content-none"
          onClick={onClose}
        ></div>
      </Portal>
    </>
  );
};
