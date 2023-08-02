import { request } from "@/lib/axios";
import { SingleDataResponse } from "@/types";

import { SocialType, TokenResponse } from "../types";

export interface SocialLoginRequest {
  socialType: SocialType;
  code: string;
}

const authApi = {
  socialLogin({
    socialType,
    code,
  }: SocialLoginRequest): Promise<SingleDataResponse<TokenResponse>> {
    return request.post(`/auth/${socialType}`, { socialType, code });
  },

  refresh(): Promise<SingleDataResponse<TokenResponse>> {
    return request.get("/auth/refresh");
  },

  logout() {
    return request.post<void>("/auth/logout");
  },
};

export default authApi;
