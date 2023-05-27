import request from "@/service/request";

export const getTagList = ({ page, size, sortBy }) => {
  return request.get("/tags", { params: { page, size, sortBy } });
};
