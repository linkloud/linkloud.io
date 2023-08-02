import { request } from "@/lib/axios";
import { MultiDataResponse } from "@/types";

import { Tag, TagSort } from "../types";

export interface GetTagsReqeust {
  page: number;
  size: number;
  sortBy: TagSort;
}

const tagApi = {
  getList({
    page,
    size,
    sortBy,
  }: GetTagsReqeust): Promise<MultiDataResponse<Tag[]>> {
    return request.get("/tags", { params: { page, size, sortBy } });
  },
};

export default tagApi;
