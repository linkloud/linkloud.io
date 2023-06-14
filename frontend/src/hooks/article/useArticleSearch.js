import { useNavigate } from "react-router-dom";

const useArticleSearch = () => {
  const navigate = useNavigate();

  const handleSearch = (keyword) => {
    const query = createSearchUrl(keyword);
    navigate(query);
  };

  /** 문자열 keyword로 검색 쿼리 스트링을 만든다 */
  const createSearchUrl = (keyword) => {
    // 검색어와 태그(대괄호)를 분리
    const regex = /\[.*?\]|[^[\]\s]+/g;
    const wordSet = new Set(keyword.match(regex)); // 중복 태그 제거
    const words = Array.from(wordSet);

    const tags = [];
    let fullKeyword = "";

    words.forEach((word) => {
      if (word.startsWith("[") && word.endsWith("]")) {
        const tag = word.substring(1, word.length - 1);
        tags.push(tag);
      } else {
        fullKeyword = word;
      }
    });

    let query = "/search?";

    if (fullKeyword) {
      query += `keyword=${fullKeyword}`;
    }

    tags.forEach((tag, index) => {
      query += `${index > 0 || fullKeyword ? "&" : ""}tag=${tag}`;
    });

    return query;
  };

  return {
    handleSearch,
  };
};

export default useArticleSearch;
