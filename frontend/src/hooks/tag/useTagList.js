import { useState, useEffect } from "react";

import { getTagList } from "@/service/api/tag";
import { TAG_SORT_OPTIONS } from "@/common/constants";
import { toast } from "react-toastify";

const useTagList = ({
  page = 1,
  size = 10,
  initialSortBy = TAG_SORT_OPTIONS.POPULARITY,
}) => {
  const [tagList, setTagList] = useState([]);
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
    fetchTagList(tagPageInfo.page, tagPageInfo.size, sortBy);
  }, [page, size, sortBy]);

  const fetchTagList = async (page, size, sortBy) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await getTagList({ page, size, sortBy });
      setTagList(data);
      setTagPageInfo(pageInfo);
    } catch (err) {
      setError(err);
      toast.error(
        "태그 조회 중 서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요."
      );
    } finally {
      setLoading(false);
    }
  };

  const handlePrevTagList = () => {
    if (tagPageInfo.page > 1) {
      setTagPageInfo((prev) => ({
        ...prev,
        page: prev.page - 1,
      }));
    }
  };

  const handleNextTagList = () => {
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
    tagList,
    tagPageInfo,
    loading,
    fetchTagListError: error,
    handlePrevTagList,
    handleNextTagList,
    handleChangeSortOption,
  };
};

export default useTagList;
