import axios, { AxiosError, AxiosRequestConfig } from "axios";
import qs from "qs";

import useAuthStore from "@/stores/useAuthStore";

import { log } from "@/utils/log";

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

/** 응답 인터셉터 */
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
    // if (data && data.message === ERROR_CODE.ACCESS_EXPIRED_TOKEN) {
    //   return handleExpiredToken(error, request);
    // }

    // 서버 예외
    // if (data.message || data.fieldErrors.length > 0) {
    //   log(data);
    //   return Promise.reject(data);
    // }

    log(error);
    error.status = 500;

    return Promise.reject(error);
  }
);

// ---------------------------------------------------------------------------
// 토큰 관리
// ---------------------------------------------------------------------------
let isRefreshing = false; // 리프레시 중인지 여부
let failedApiQueue = []; // 리프레시 중 쌓인 API 큐

const processQueue = (error, token = null) => {
  failedApiQueue.forEach((req) => {
    if (error) {
      req.reject(error);
    } else {
      req.resolve(token);
    }
  });

  failedApiQueue = [];
};

// 토큰 만료 처리
const handleExpiredToken = async (error: any, request) => {
  if (isRefreshing) {
    return handleApiRefreshing(error, request);
  }

  log("🍪 try refresh ");
  request._retry = true;
  isRefreshing = true;
  const initToken = useAuthStore.getState().initToken;
  initToken();

  const refresh = useAuthStore.getState().refresh;

  return new Promise((resolve, reject) => {
    refresh(request)
      .then(() => {
        const token = useAuthStore.getState().token;
        request.headers.Authorization = `Bearer ${token}`;
        processQueue(null, token);
        resolve(instance(request));
      })
      .catch((err) => {
        processQueue(err, null);
        reject(err);
      })
      .finally(() => {
        isRefreshing = false;
      });
  });
};

// 토큰 갱신 중 처리
const handleApiRefreshing = async (error, request) => {
  return new Promise((resolve, reject) => {
    failedApiQueue.push({ resolve, reject });
  })
    .then((token) => {
      request.headers.Authorization = `Bearer ${token}`;
      return instance(request);
    })
    .catch((error) => {
      return Promise.reject(error.message);
    });
};

// ---------------------------------------------------------------------------
// 로그
// ---------------------------------------------------------------------------

const logRequest = (request) => {
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
  const requestUrl = `[${method.toUpperCase()}] ${url}${queryParams}`;
  log(requestUrl);

  if (data) {
    log("📦 request data ");
    log(data);
  }
};

const logResponse = (response) => {
  if (!import.meta.env.DEV) return;
  const baseUrl = import.meta.env.VITE_API;

  const requestUrl = response.request.responseURL.split(baseUrl)[1] ?? null;

  const { data } = response;

  log(`📦 response : ${requestUrl}`);

  if (data) {
    log(data);
  }
};

const logError = (error: AxiosError) => {
  const request = error.config;
  if (request && request.method) {
    log(`🚨 error [${request.method.toUpperCase()}] ${request.url}`);
    log(error);
  }
};
