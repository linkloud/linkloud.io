import { request } from "@/lib/axios";

import { SocialType, TokenResponse } from "../types";
import { SingleDataResponse } from "@/types";

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
    return request.get<void>("/auth/logout");
  },
};

export default authApi;
