import request from "@/service/request";

export const socialLogin = (type, code) => {
  return request.post(`/auth/login`, { type, code });
};
