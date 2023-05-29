import request from "@/service/request";

export const socialLogin = (socialType, code) => {
  return request.post(`/auth/${socialType}`, { socialType, code });
};

export const refresh = (refreshToken) => {
  return request.post(`/auth/refresh`, { refreshToken, tokenType: "Bearer " });
};
