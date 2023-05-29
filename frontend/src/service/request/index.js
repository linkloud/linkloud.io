import axios from "axios";
import { log } from "@/common/utils/";

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
        log("[            🚨 error              ]");

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

    log("[            🚀 request            ]");
    const { method, url, params, data } = request;

    let queryParams = "";
    if (params) {
      queryParams = Object.keys(params)
        .map((k) => encodeURIComponent(k) + "=" + encodeURIComponent(params[k]))
        .join("&");
      queryParams = "?" + queryParams;
    }

    log(`[${method.toUpperCase()}] ${url}${queryParams}`);

    if (data) {
      log("[request data]");
      log(data);
    }
  }

  logResponse(response) {
    if (!import.meta.env.DEV) return;
    log("[            📦 response           ]");
    const { data } = response;

    if (data) {
      log("[response data]");
      log(data);
    }
  }
}

export default new Request(config);
