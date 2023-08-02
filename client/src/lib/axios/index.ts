import axios from "axios";
import * as qs from "qs";

import useAuthStore from "@/stores/useAuthStore";
import { log } from "@/utils/log";

import {
  handleExpiredToken,
  logError,
  logRequest,
  logResponse,
} from "./helper";

export const request = axios.create({
  baseURL: import.meta.env.VITE_API,
  timeout: 3 * 1000,
  headers: { "Content-Type": "application/json" },
  withCredentials: true,
  paramsSerializer: (params) => qs.stringify(params, { arrayFormat: "repeat" }),
});

// 요청 인터셉터
request.interceptors.request.use((request) => {
  logRequest(request);

  const token = useAuthStore.getState().token;
  if (token) request.headers.Authorization = `Bearer ${token}`;

  return request;
});

// 응답 인터셉터
request.interceptors.response.use(
  (response) => {
    logResponse(response);

    const { data } = response;

    if (data) {
      return data;
    }

    return response;
  },

  async (error) => {
    logError(error);

    const request = error.config;
    const { data } = error.response;

    // 액세스 토큰 만료 처리
    if (data && data.message === "Expired access token") {
      return handleExpiredToken(error, request);
    }

    // 서버 예외
    if (data.message || data.fieldErrors.length > 0) {
      log(data);
      return Promise.reject(data);
    }

    log(error);
    error.status = 500;

    return Promise.reject(error);
  },
);
