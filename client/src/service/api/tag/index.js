import request from "@/service/request";

const tagApi = {
  getList: ({ page, size, sortBy }) =>
    request.get("/tags", { params: { page, size, sortBy } }),
};

export default tagApi;
