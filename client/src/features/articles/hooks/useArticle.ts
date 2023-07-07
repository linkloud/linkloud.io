import { useState } from "react";

import articleApi from "../apis";

export const useArticle = () => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const handleClickArticle = async (id: number) => {
    try {
      setLoading(true);
      const { data } = await articleApi.get(id);

      if (data.url) {
        window.open(data.url, "_blank", "noreferrer");
      }
    } catch (e) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  return { articleLoading: loading, articleError: error, handleClickArticle };
};
