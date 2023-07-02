import request from "@/service/request";

const memberApi = {
  me: ({ token }) =>
    request.get(`/member/me`, {
      headers: { Authorization: `Bearer ${token}` },
    }),
};

export default memberApi;
