import { useState, useEffect } from "react";

import { PageInfo } from "@/types";

import articleApi, { GetArticlesRequest } from "../apis";
import { Article } from "../types";

export const useArticles = ({ page = 1 }: GetArticlesRequest) => {
  const [articles, setArticles] = useState<Article[]>([]);
  const [pageInfo, setPageInfo] = useState<PageInfo>({
    page,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchArticles = async ({ page }: GetArticlesRequest) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await articleApi.getList({ page });
      setArticles(data);
      setPageInfo(pageInfo);
    } catch (e) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  const handlePrevArticles = () => {
    if (pageInfo.page > 1) {
      setPageInfo((prev) => ({
        ...prev,
        page: prev.page - 1,
      }));
    }
  };

  const handleNextArticles = () => {
    if (pageInfo.page < pageInfo.totalPages) {
      setPageInfo((prev) => ({
        ...prev,
        page: prev.page + 1,
      }));
    }
  };

  useEffect(() => {
    fetchArticles({ page: pageInfo.page });
  }, [pageInfo.page]);

  return {
    articles,
    articlesPageInfo: pageInfo,
    articlesLoading: loading,
    articlesError: error,
    handlePrevArticles,
    handleNextArticles,
  };
};
