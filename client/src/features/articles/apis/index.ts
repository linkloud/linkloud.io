import { request } from "@/lib/axios";
import { MultiDataResponse } from "@/types";

import { Article } from "../types";

export interface CreateArticleRequest {
  title: string;
  url: string;
  description: string;
  tags: string[];
}

export interface GetArticlesRequest {
  page: number;
}

export interface SearchArticleReqeust {
  keyword: string | null;
  tags: string[] | null;
  page: number;
}

const articleApi = {
  create: ({ title, url, description, tags }: CreateArticleRequest) => {
    return request.post("/article", {
      title,
      url,
      description,
      tags,
    });
  },

  get(id: number) {
    return request.get(`/article/${id}`);
  },

  getList({ page }: GetArticlesRequest): Promise<MultiDataResponse<Article[]>> {
    return request.get("/article", { params: { page } });
  },

  search({
    keyword,
    tags,
    page = 1,
  }: SearchArticleReqeust): Promise<MultiDataResponse<Article[]>> {
    return request.get("/article/search", {
      params: { keyword, tags, page },
    });
  },
};

export default articleApi;
