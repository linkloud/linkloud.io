import { useState, useEffect } from "react";

import tagApi from "@/service/api/tag";
import { isServerError } from "@/service/request/helper";

const useTags = ({ page = 1, size = 10, sortBy: initialSortBy }) => {
  const [tags, setTags] = useState([]);
  const [tagPageInfo, setTagPageInfo] = useState({
    page,
    size,
    totalElements: 0,
    totalPages: 0,
  });
  const [sortBy, setSortBy] = useState(initialSortBy);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    fetchTags(tagPageInfo.page, tagPageInfo.size, sortBy);
  }, [page, size, sortBy]);

  const fetchTags = async (page, size, sortBy) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await tagApi.getList({ page, size, sortBy });
      setTags(data);
      setTagPageInfo(pageInfo);
    } catch (error) {
      if (isServerError(error)) setError(error);
    } finally {
      setLoading(false);
    }
  };

  const handlePrevTags = () => {
    if (tagPageInfo.page > 1) {
      setTagPageInfo((prev) => ({
        ...prev,
        page: prev.page - 1,
      }));
    }
  };

  const handleNextTags = () => {
    if (tagPageInfo.page < tagPageInfo.totalPages) {
      setTagPageInfo((prev) => ({
        ...prev,
        page: prev.page + 1,
      }));
    }
  };

  /** 태그 정렬 옵션을 변경후 1페이지부터 태그를 요청 */
  const handleChangeSortOption = (sortOption) => {
    setSortBy(sortOption);
    setTagPageInfo((prev) => ({
      ...prev,
      page: 1, // 페이지를 1로 초기화
    }));
  };

  return {
    tags,
    tagPageInfo,
    loading,
    fetchTagsError: error,
    handlePrevTags,
    handleNextTags,
    handleChangeSortOption,
  };
};

export default useTags;
