import { create } from "zustand";

import authApi, { SocialLoginRequest } from "@/features/auth/apis";
import memberApi from "@/features/members/apis";

import { Member } from "@/features/members/types";

import { log } from "@/utils/log";

interface AuthState {
  loading: boolean;
  token: string;
  userInfo: Member;
  initToken: () => void;
  socialLogin: (data: SocialLoginRequest) => Promise<void>;
  refresh: () => Promise<void>;
  logout: () => Promise<void>;
  fetchUserInfo: () => Promise<void>;
  setToken: (token: string) => void;
  isAuth: () => boolean;
}

const initialUserInfo: Member = {
  id: 0,
  nickname: "",
  picture: "",
  role: "USER",
};

const useAuthStore = create<AuthState>()((set, get) => ({
  loading: true,
  token: "",
  userInfo: {
    ...initialUserInfo,
  },
  initToken: () => {
    get().setToken("");
  },
  socialLogin: async ({ socialType, code }: SocialLoginRequest) => {
    const { data } = await authApi.socialLogin({ socialType, code });
    get().setToken(data.accessToken);
    get().fetchUserInfo();
    return;
  },
  refresh: async () => {
    try {
      set({ loading: true });

      const { data } = await authApi.refresh();
      get().setToken(data.accessToken);

      await get().fetchUserInfo();
    } catch (e: any) {
      throw new Error(e.message);
    } finally {
      set({ loading: false });
    }
  },
  logout: async () => {
    await authApi.logout();
    set({ token: "", userInfo: { ...initialUserInfo } });
  },
  fetchUserInfo: async () => {
    const token = get().token;
    const { data } = await memberApi.me(token);
    set((state) => ({ ...state, userInfo: { ...data } }));
  },
  setToken: (token) => set((state) => ({ ...state, token })),
  isAuth: () => {
    return get().userInfo.role !== "USER";
  },
}));

export default useAuthStore;
