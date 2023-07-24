import { request } from "@/lib/axios";

import { Member } from "../types";
import { Article } from "@/features/articles/types";
import { Tag } from "@/features/tags/types";
import { MultiDataResponse, SingleDataResponse } from "@/types";

export type MyArticleSort = "latest" | "title" | "read" | "reading";

export interface GetMemeberArticlesReqeust {
  id: number;
  page: number;
  tag?: string;
  sortBy: MyArticleSort;
}

export interface GetMemeberTagsReqeust {
  id: number;
  page: number;
  size: number;
}

const memberApi = {
  me(token: string): Promise<SingleDataResponse<Member>> {
    return request.get("/member/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
  },

  getArticles({
    id,
    page,
    tag,
    sortBy,
  }: GetMemeberArticlesReqeust): Promise<MultiDataResponse<Article[]>> {
    return request.get(`/member/${id}/articles`, {
      params: { page, tag, sortBy },
    });
  },

  getTags({
    id,
    page,
    size,
  }: GetMemeberTagsReqeust): Promise<MultiDataResponse<Tag[]>> {
    return request.get(`/member/${id}/tags`, {
      params: { page, size },
    });
  },
};

export default memberApi;
