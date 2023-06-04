import axios from "axios";
import { log } from "@/common/utils/";
import useAuthStore from "@/stores/useAuthStore";

const config = {
  baseURL: import.meta.env.VITE_API,
  timeout: 3 * 1000,
  headers: { "Content-Type": "application/json" },
  withCredentials: true,
};

class Request {
  constructor(config) {
    this.instance = axios.create(config);
    this.setInterceptor();
  }

  setInterceptor() {
    /** request */
    this.instance.interceptors.request.use((request) => {
      this.logRequest(request);
      const token = useAuthStore.getState().token;
      if (token) request.headers.Authorization = `Bearer ${token}`;
      return request;
    });

    /** response */
    this.instance.interceptors.response.use(
      // 성공 응답 처리
      (response) => {
        this.logResponse(response);

        const { data } = response;

        if (data) {
          return data;
        }

        return response;
      },
      // 실패 응답 처리
      (error) => {
        log("🚨  error");

        if (error.response && error.response.data) {
          log(error.response.data);
          return Promise.reject(error.response.data);
        }
        log(error);
        return Promise.reject(error.message);
      }
    );
  }

  get(url, config) {
    return this.instance.get(url, config);
  }

  post(url, data, config) {
    return this.instance.post(url, data, config);
  }

  put(url, data, config) {
    return this.instance.put(url, data, config);
  }

  patch(url, data, config) {
    return this.instance.patch(url, data, config);
  }

  delete(url, config) {
    return this.instance.delete(url, config);
  }

  logRequest(request) {
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
  }

  logResponse(response) {
    if (!import.meta.env.DEV) return;
    const baseUrl = import.meta.env.VITE_API;
    const requestUrl = response.request.responseURL.split(baseUrl)[1] ?? null;

    const { data } = response;

    log(`📦 response : ${requestUrl}`);

    if (data) {
      log(data);
    }
  }
}

export default new Request(config);
