import { Article, ReadStatus } from "@/features/articles/types";
import { Tag } from "@/features/tags/types";
import { request } from "@/lib/axios";
import { MultiDataResponse, SingleDataResponse } from "@/types";

import { Member } from "../types";

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

export interface UpdateArticleReadStatus {
  id: number;
  articleId: number;
  status: ReadStatus;
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

  updateReadStatus({ id, articleId, status }: UpdateArticleReadStatus) {
    return request.patch(`/member/${id}/article-status/${articleId}`, {
      articleStatus: status,
    });
  },
};

export default memberApi;
