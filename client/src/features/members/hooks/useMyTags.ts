import { useEffect, useState } from "react";

import { Tag } from "@/features/tags/types";
import { PageInfo } from "@/types";

import memberApi, { GetMemeberTagsReqeust } from "../apis";

export const useMyTags = (memberId: number) => {
  const [tags, setTags] = useState<Tag[]>([]);
  const [pageInfo, setPageInfo] = useState<PageInfo>({
    page: 1,
    size: 20,
    totalElements: 0,
    totalPages: 0,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchTags = async ({ id, page, size }: GetMemeberTagsReqeust) => {
    try {
      setLoading(true);
      const { data, pageInfo } = await memberApi.getTags({
        id,
        page,
        size,
      });
      setTags(data);
      setPageInfo(pageInfo);
    } catch (e: any) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTags({ id: memberId, page: pageInfo.page, size: pageInfo.size });
  }, [pageInfo.page]);

  return {
    tags,
    tagsLoading: loading,
    tagsError: error,
  };
};
