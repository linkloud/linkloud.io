import { useState, useEffect } from "react";

import { getTags } from "@/service/api/tag";

const useTags = ({ page = 1, size = 10, sortBy = "popularity" }) => {
  const [tags, setTags] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchTags = async () => {
      try {
        setLoading(true);
        const data = await getTags({ page, size, sortBy });
        setTags(data);
        setLoading(false);
      } catch (err) {
        setError(err);
        setLoading(false);
      }
    };

    fetchTags();
  }, [page, sortBy, page]);

  return { tags, loading, error };
};

export default useTags;
