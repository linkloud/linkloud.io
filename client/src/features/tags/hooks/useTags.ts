import { useState, useEffect } from "react";

import tagApi, { GetTagsReqeust } from "../apis";

import { Tag, TagSort } from "../types";
import { PageInfo } from "@/types";

export const useTags = ({
  page = 1,
  size = 10,
  sortBy: initialSortBy = "popularity",
}: GetTagsReqeust) => {
  const [tags, setTags] = useState<Tag[]>([]);
  const [pageInfo, setPageInfo] = useState<PageInfo>({
    page,
    size,
    totalElements: 0,
    totalPages: 0,
  });
  const [sortBy, setSortBy] = useState<TagSort>(initialSortBy);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchTags = async ({ page, size, sortBy }: GetTagsReqeust) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await tagApi.getList({ page, size, sortBy });
      setTags(data);
      setPageInfo(pageInfo);
    } catch (e: any) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  const handlePrevTags = () => {
    if (pageInfo.page > 1) {
      setPageInfo((prev) => ({
        ...prev,
        page: prev.page - 1,
      }));
    }
  };

  const handleNextTags = () => {
    if (pageInfo.page < pageInfo.totalPages) {
      setPageInfo((prev) => ({
        ...prev,
        page: prev.page + 1,
      }));
    }
  };

  const handleTagsSortOption = (option: TagSort) => {
    setSortBy(option);
    setPageInfo((prev) => ({
      ...prev,
      page: 1,
    }));
  };

  useEffect(() => {
    fetchTags({ page: pageInfo.page, size: pageInfo.size, sortBy });
  }, [pageInfo.page, pageInfo.size, sortBy]);

  return {
    tags,
    tagsPageInfo: pageInfo,
    tagsSortOption: sortBy,
    tagsLoading: loading,
    tagsError: error,
    handlePrevTags,
    handleNextTags,
    handleTagsSortOption,
  };
};
