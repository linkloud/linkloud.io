// useSearchValidation.js
import { useState } from "react";

const useArticleSearchValidation = () => {
  const [searchValidationError, setSearchValidationError] = useState("");

  const validateSearch = (keyword) => {
    if (!keyword) {
      setSearchValidationError("검색어를 입력해 주세요.");
      return false;
    }

    // 검색어와 태그(대괄호)를 분리
    const regex = /\[.*?\]|[^[\]\s]+/g;
    const wordList = keyword.match(regex);
    const tagList = [];

    wordList.forEach((word) => {
      if (word.startsWith("[") && word.endsWith("]")) {
        const tag = word.substring(1, word.length - 1);
        tagList.push(tag);
      }
    });

    if (tagList.length > 5) {
      setSearchValidationError("태그의 개수는 최대 5개까지 가능합니다.");
      return false;
    }

    // 검색어가 유효한 경우
    setSearchValidationError("");
    return true;
  };

  return { searchValidationError, setSearchValidationError, validateSearch };
};

export default useArticleSearchValidation;
