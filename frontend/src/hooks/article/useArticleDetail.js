import { getArticleDetail } from "@/service/api";

const useArticleDetail = () => {
  const handleArticleClick = async (id) => {
    try {
      const { data } = await getArticleDetail({ id });

      if (data.url) {
        window.open(data.url, "_blank", "noreferrer");
      }
    } catch (e) {}
  };

  return { handleArticleClick };
};

export default useArticleDetail;
