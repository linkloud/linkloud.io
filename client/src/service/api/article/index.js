import request from "@/service/request";

const articleApi = {
  create: ({ title, url, description, tags }) => {
    return request.post("/article", {
      title,
      url,
      description,
      tags,
    });
  },

  get: ({ id }) => request.get(`/article/${id}`),

  getList: ({ page }) => request.get("/article", { params: { page } }),

  search: ({ keyword, keywordType = "titleAndDescription", page = 1 }) =>
    request.get("/article/search", { params: { keyword, keywordType, page } }),
};

export default articleApi;
