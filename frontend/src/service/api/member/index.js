import request from "@/service/request";

export const me = (token) => {
  return request.get(`/member/me`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
