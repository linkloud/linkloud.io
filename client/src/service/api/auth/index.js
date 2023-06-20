import request from "@/service/request";

const authApi = {
  socialLogin: ({ socialType, code }) =>
    request.post(`/auth/${socialType}`, { socialType, code }),

  refresh: () => request.post("/auth/refresh"),

  logout: () => request.get("/auth/logout"),
};

export default authApi;
