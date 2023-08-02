import { useState, useEffect } from "react";

import { Article, ReadStatus } from "@/features/articles/types";
import { PageInfo } from "@/types";

import memberApi, { GetMemeberArticlesReqeust, MyArticleSort } from "../apis";

export const useMyArticles = (memberId: number) => {
  const [articles, setArticles] = useState<Article[]>([]);
  const [pageInfo, setPageInfo] = useState<PageInfo>({
    page: 1,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });
  const [tag, setTag] = useState("");
  const [sortBy, setSortBy] = useState<MyArticleSort>("latest");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchArticles = async ({
    id,
    page,
    tag,
    sortBy,
  }: GetMemeberArticlesReqeust) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await memberApi.getArticles({
        id,
        page,
        tag,
        sortBy,
      });
      setArticles(data);
      setPageInfo(pageInfo);
    } catch (e: any) {
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

  const handleChangeTag = (tag: string) => {
    setTag(tag);
  };

  const handleChangeSort = (sort: MyArticleSort) => {
    setSortBy(sort);
  };

  const handleReadStatus = (id: number, newStatus: ReadStatus) => {
    setArticles((prev) =>
      prev.map((article) =>
        article.id === id ? { ...article, readStatus: newStatus } : article,
      ),
    );
  };

  useEffect(() => {
    if (tag.length > 0)
      fetchArticles({ id: memberId, tag, page: pageInfo.page, sortBy });
    else fetchArticles({ id: memberId, page: pageInfo.page, sortBy });
  }, [pageInfo.page, tag, sortBy]);

  return {
    articles,
    articlesPageInfo: pageInfo,
    tag,
    sortBy,
    articlesLoading: loading,
    articlesError: error,
    handlePrevArticles,
    handleNextArticles,
    handleChangeTag,
    handleChangeSort,
    handleReadStatus,
  };
};
