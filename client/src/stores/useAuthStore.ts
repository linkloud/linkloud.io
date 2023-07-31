import { create } from "zustand";
import { persist } from "zustand/middleware";

import { Member } from "@/features/members/types";

import authApi, { SocialLoginRequest } from "@/features/auth/apis";
import memberApi from "@/features/members/apis";

interface AuthStore {
  token: string;
  user: Member;
  isRefreshing: boolean;
  actions: AuthAction;
}

interface AuthAction {
  isLoggedIn: () => boolean;
  reset: () => void;
  socialLogin: (data: SocialLoginRequest) => Promise<boolean>;
  refresh: () => Promise<{ success: boolean; error?: Error }>;
  fetchUserInfo: (token: string) => Promise<void>;
  logout: () => Promise<{ success: boolean; error?: Error }>;
}

const initialUserInfo: Member = {
  id: 0,
  nickname: "",
  picture: "",
  role: "USER",
};

const useAuthStore = create<AuthStore>()(
  persist(
    (set, get) => ({
      token: "",
      user: {
        id: 0,
        nickname: "",
        picture: "",
        role: "USER",
      },
      isRefreshing: false,

      actions: {
        isLoggedIn: () => {
          return get().user.role !== "USER";
        },
        reset: () =>
          set((state) => ({
            ...state,
            token: "",
            user: { ...initialUserInfo },
          })),
        socialLogin: async ({ socialType, code }: SocialLoginRequest) => {
          try {
            const { data } = await authApi.socialLogin({ socialType, code });
            const token = data.accessToken;
            set((state) => ({ ...state, token }));
            get().actions.fetchUserInfo(token);
            return true;
          } catch (e) {
            return false;
          }
        },
        refresh: async () => {
          try {
            if (get().isRefreshing) {
              throw new Error("already refreshing");
            }

            set((state) => ({ ...state, isRefreshing: true }));
            const { data } = await authApi.refresh();
            const token = data.accessToken;
            set((state) => ({ ...state, token }));
            return { success: true };
          } catch (e) {
            if (e instanceof Error && e.message === "already refreshing") {
              return { success: true };
            }

            return { success: false, error: e as Error };
          } finally {
            set((state) => ({ ...state, isRefreshing: false }));
          }
        },
        fetchUserInfo: async (token: string) => {
          const { data } = await memberApi.me(token);
          set((state) => ({ ...state, user: { ...data } }));
        },
        logout: async () => {
          try {
            get().actions.reset();
            await authApi.logout();
            return { success: true };
          } catch (e) {
            return { success: false, error: e as Error };
          }
        },
      },
    }),
    {
      name: "auth",
      partialize: (state) => ({ user: state.user }),
    }
  )
);

export const useIsRefreshing = () =>
  useAuthStore((state) => state.isRefreshing);
export const useToken = () => useAuthStore((state) => state.token);
export const useUser = () => useAuthStore((state) => state.user);

export const useAuthActions = () => useAuthStore((state) => state.actions);

export default useAuthStore;
