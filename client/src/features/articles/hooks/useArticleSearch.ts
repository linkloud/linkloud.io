import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

import articleApi, { SearchArticleReqeust } from "../apis";

import { Article } from "../types";
import { PageInfo } from "@/types";

export const useArticleSearch = ({ page = 1 }) => {
  const [currentPage, setCurrentPage] = useState(page);
  const [articles, setArticles] = useState<Article[]>([]);
  const [pageInfo, setPageInfo] = useState<PageInfo>({
    page,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [searchParams] = useSearchParams();
  const searchKeyword = searchParams.get("keyword");
  const searchTags = searchParams.getAll("tags");

  const search = async (params: SearchArticleReqeust) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await articleApi.search({ ...params });
      setArticles(data);
      setPageInfo(pageInfo);
    } catch (e) {
    } finally {
      setLoading(false);
    }
  };

  const handleSearchPrev = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleSearchNext = () => {
    if (currentPage < pageInfo.totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  useEffect(() => {
    search({
      keyword: searchKeyword,
      tags: searchTags,
      page: pageInfo.page,
    });
  }, [searchParams, currentPage]);

  return {
    searchKeyword,
    searchTags,
    searchedArticles: articles,
    searchedArticlesPageInfo: pageInfo,
    searchArticlesLoading: loading,
    searchArticlesError: error,
    handleSearchPrev,
    handleSearchNext,
  };
};
