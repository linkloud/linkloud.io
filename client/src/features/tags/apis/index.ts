import { request } from "@/lib/axios";

import { Tag, TagSort } from "../types";
import { MultiDataResponse } from "@/types";

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
