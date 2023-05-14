import { create } from "zustand";

import { socialLogin, me } from "@/service/api";

import { ROLE } from "@/common/constants";

const useAuthStore = create((set, get) => ({
  token: "",
  userInfo: {
    id: 0,
    nickname: "",
    role: ROLE.GUEST,
    picture: "",
  },
  // TODO: error handling
  socialLogin: async (socialType, code) => {
    const { accessToken: token } = await socialLogin(socialType, code);
    get().setToken(token);
    get().fetchUserInfo();
  },
  logout: () => {
    console.log("call");
    console.log(get());
    set(initialState);
  },
  setToken: (token) => set({ token }),
  fetchUserInfo: async () => {
    const userInfo = await me(get().token);
    set((state) => ({ ...state, userInfo: userInfo }));
  },
}));

export default useAuthStore;
