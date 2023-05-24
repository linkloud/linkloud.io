import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import useModal from "../useModal";

const useArticleSearch = () => {
  const [searchValidationErrMsg, setSearchValidationErrMsg] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (searchValidationErrMsg) {
      openSearchValidationModal();
    }
  }, [searchValidationErrMsg]);

  const {
    isOpened: isSearchValidationErrorModalOpened,
    openModal: openSearchValidationModal,
    closeModal: closeSearchValidationModal,
  } = useModal();

  const handleSearch = (searchKeyword) => {
    try {
      const query = createSearchUrl(searchKeyword);
      navigate(query);
    } catch (e) {
      setSearchValidationErrMsg(e.message);
    }
  };

  /** 에러메세지 초기화 후 모달을 닫는다. */
  const handleCloseSearchValidationModal = () => {
    setSearchValidationErrMsg("");
    closeSearchValidationModal();
  };

  /**
   * 문자열 searchKeyword로 검색 쿼리 스트링을 만든다.
   */
  const createSearchUrl = (searchKeyword) => {
    if (!searchKeyword) throw new Error("검색어를 입력해 주세요.");

    // 검색어와 태그(대괄호)를 분리
    const regex = /\[.*?\]|[^[\]\s]+/g;
    const wordList = searchKeyword.match(regex);

    const tagList = [];
    let keyword = "";

    wordList.forEach((word) => {
      if (word.startsWith("[") && word.endsWith("]")) {
        const tag = word.substring(1, word.length - 1);
        tagList.push(tag);
      } else {
        keyword = word;
      }
    });

    if (tagList.length > 5)
      throw new Error("태그의 개수는 최대 5개까지 가능합니다.");

    let query = "/search?";

    if (keyword) {
      query += `keyword=${keyword}`;
    }

    tagList.forEach((tag, index) => {
      query += `${index > 0 || keyword ? "&" : ""}tag=${tag}`;
    });

    return query;
  };

  return {
    searchValidationErrMsg,
    isSearchValidationErrorModalOpened,
    handleSearch,
    handleCloseSearchValidationModal,
  };
};

export default useArticleSearch;
