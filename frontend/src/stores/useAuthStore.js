import { create } from "zustand";
import Cookies from "js-cookie";

import { socialLogin, me, refresh } from "@/service/api";

import { ROLE } from "@/common/constants";

const initialState = {
  nickname: "",
  picture: "",
  role: ROLE.ANONYMOUS,
};

const useAuthStore = create((set, get) => ({
  token: "",
  userInfo: {
    ...initialState,
  },
  socialLogin: async (socialType, code) => {
    const { data } = await socialLogin(socialType, code);
    const { accessToken, refreshToken } = data;
    get().setToken(accessToken);
    get().fetchUserInfo();
    Cookies.set("refreshToken", refreshToken);
  },
  logout: () => {
    set({ token: "", userInfo: { ...initialState } });
    Cookies.remove("refreshToken");
  },
  setToken: (token) => set({ token }),
  initUserInfo: async () => {
    try {
      const { data } = await refresh();
      const { accessToken, refreshToken: newRefreshToken } = data;

      get().setToken(accessToken);
      Cookies.set("refreshToken", newRefreshToken);

      await get().fetchUserInfo();
    } catch (e) {}
  },
  fetchUserInfo: async () => {
    const token = get().token;
    const { data } = await me(token);
    set((state) => ({ ...state, userInfo: { ...data } }));
  },
}));

export default useAuthStore;
