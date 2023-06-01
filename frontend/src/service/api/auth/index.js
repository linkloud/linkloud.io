import request from "@/service/request";

export const socialLogin = (socialType, code) => {
  return request.post(`/auth/${socialType}`, { socialType, code });
};

export const refresh = () => {
  return request.post(`/auth/refresh`);
};
