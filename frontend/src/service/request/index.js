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
    this.instance.interceptors.request.use((req) => {
      log("🚀 request ---------------- ");
      log(req);

      return req;
    });

    /** response */
    this.instance.interceptors.response.use(
      // 성공 응답 처리
      (response) => {
        const { data } = response;

        if (data && data.data) {
          log("📦 data ----------------");
          log(data.data);
          return data.data;
        }
        return response;
      },
      // 실패 응답 처리
      (error) => {
        log("🚨 api error ----------------");
        log(error);

        if (error.response && error.response.data) {
          return Promise.reject(error.response.data);
        }

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
}

export default new Request(config);
