import articleApi from "@/service/api/article";

const useArticleDetail = () => {
  const handleArticleClick = async (id) => {
    try {
      const { data } = await articleApi.get({ id });

      if (data.url) {
        window.open(data.url, "_blank", "noreferrer");
      }
    } catch (e) {}
  };

  return { handleArticleClick };
};

export default useArticleDetail;
