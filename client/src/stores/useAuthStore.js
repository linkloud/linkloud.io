import { create } from "zustand";
import { toast } from "react-toastify";

import authApi from "@/service/api/auth";
import memberApi from "@/service/api/member";
import { ROLE, ERROR_CODE } from "@/common/constants";

const initialUserInfo = {
  nickname: "",
  picture: "",
  role: ROLE.USER,
};

const useAuthStore = create((set, get) => ({
  isAuthLoading: true,
  token: "",
  userInfo: {
    ...initialUserInfo,
  },
  initUserInfo: async () => {
    try {
      const { data } = await authApi.refresh();
      const { accessToken } = data;
      get()._setToken(accessToken);
      await get()._fetchUserInfo();
    } catch {
      // 만료
    } finally {
      set({ isAuthLoading: false });
    }
  },
  initToken: () => {
    get()._setToken("");
  },
  socialLogin: async (socialType, code) => {
    try {
      const { data } = await authApi.socialLogin({ socialType, code });
      const { accessToken } = data;
      get()._setToken(accessToken);
      get()._fetchUserInfo();
    } catch (e) {
      if (e.message === ERROR_CODE.SERVER_ERROR) {
        toast.error("로그인에 실패했습니다. 잠시후에 다시 시도해주세요");
      }
    }
  },
  refresh: async () => {
    try {
      const { data } = await authApi.refresh();
      const { accessToken } = data;

      get()._setToken(accessToken);

      await get()._fetchUserInfo();
    } catch (e) {
      const { message } = e;
      if (
        message === ERROR_CODE.REFRESH_EXPIRED_COOKIE ||
        message === ERROR_CODE.REFRESH_EXPIRED_TOKEN
      ) {
        get().logout();
        toast.error("세션이 만료되었습니다. 다시 로그인 해주세요.", {
          toastId: "need login",
        });
      }
    }
  },
  logout: async () => {
    await authApi.logout();
    set({ token: "", userInfo: { ...initialUserInfo } });
  },
  _fetchUserInfo: async () => {
    const token = get().token;
    const { data } = await memberApi.me({ token });
    set((state) => ({ ...state, userInfo: { ...data } }));
  },
  _setToken: (token) => set((state) => ({ ...state, token })),
}));

export default useAuthStore;
