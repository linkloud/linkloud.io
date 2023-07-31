import {
  AxiosError,
  AxiosRequestConfig,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";

import useAuthStore from "@/stores/useAuthStore";

import { request } from ".";
import { log } from "@/utils/log";

// ---------------------------------------------------------------------------
// 토큰 관리
// ---------------------------------------------------------------------------
interface QueueItem {
  resolve: (value?: unknown) => void;
  reject: (value?: unknown) => void;
}

let failedApiQueue: QueueItem[] = []; // 리프레시 중 쌓인 API 큐

const processQueue = (error: any, token: string | null) => {
  failedApiQueue.forEach((req) => {
    if (error) {
      req.reject(error);
    } else {
      req.resolve(token);
    }
  });

  failedApiQueue = [];
};

const requestWithInstance = async (
  config: AxiosRequestConfig
): Promise<AxiosResponse> => {
  try {
    const response = await request(config);
    return response;
  } catch (error) {
    return Promise.reject(error);
  }
};

// 토큰 만료 처리
export const handleExpiredToken = async (
  error: any,
  request: InternalAxiosRequestConfig
): Promise<AxiosResponse | void> => {
  const isRefreshing = useAuthStore.getState().isRefreshing;

  if (isRefreshing) return handleApiRefreshing(error, request);

  log("🍪 try refresh ");
  const refresh = useAuthStore.getState().actions.refresh;

  return new Promise((resolve, reject) => {
    refresh()
      .then(() => {
        const token = useAuthStore.getState().token;
        request.headers.Authorization = `Bearer ${token}`;
        processQueue(null, token);
        resolve(requestWithInstance(request));
      })
      .catch((err) => {
        processQueue(err, null);
        reject(err);
      });
  });
};

// 토큰 갱신 중 처리
const handleApiRefreshing = async (error: any, request: AxiosRequestConfig) => {
  return new Promise((resolve, reject) => {
    failedApiQueue.push({ resolve, reject });
  })
    .then((token) => {
      request.headers!.Authorization = `Bearer ${token}`;
      return requestWithInstance(request);
    })
    .catch((error) => {
      return Promise.reject(error.message);
    });
};

// ---------------------------------------------------------------------------
// 로그
// ---------------------------------------------------------------------------

export const logRequest = (request: InternalAxiosRequestConfig<any>) => {
  if (!import.meta.env.DEV) return;

  const { method, url, params, data } = request;
  log("🚀 request");

  let queryParams = "";
  if (params) {
    queryParams = Object.keys(params)
      .map((k) => encodeURIComponent(k) + "=" + encodeURIComponent(params[k]))
      .join("&");
    queryParams = "?" + queryParams;
  }
  const requestUrl = `[${method?.toUpperCase()}] ${url}${queryParams}`;
  log(requestUrl);

  if (data) {
    log("📦 request data ");
    log(data);
  }
};

export const logResponse = (response: AxiosResponse) => {
  if (!import.meta.env.DEV) return;
  const baseUrl = import.meta.env.VITE_API;

  const requestUrl = response.request.responseURL.split(baseUrl)[1] ?? null;

  const { data } = response;

  log(`📦 response : ${requestUrl}`);

  if (data) {
    log(data);
  }
};

export const logError = (error: AxiosError) => {
  const request = error.config;
  if (request && request.method) {
    log(`🚨 error [${request.method.toUpperCase()}] ${request.url}`);
    log(error);
  }
};
