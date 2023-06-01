import request from "@/service/request";

export const registerArticle = ({ title, url, description }) => {
  return request.post("/article", {
    title,
    url,
    description,
  });
};

export const getArticleList = ({ page }) => {
  return request.get("/article", { params: { page } });
};
