import request from "@/service/request";

export const getTags = ({ page, size, sortBy }) => {
  return request.get("/tags", { params: { page, size, sortBy } });
};
