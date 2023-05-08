import { create } from "zustand";

const useUserStore = create((set) => ({
  id: 0,
  setId: (id) => set({ id: id }),
  token: "",
  setToken: (token) => set({ token: token }),
  role: "guest",
  setRole: (role) => set({ role: role }),
  profileImage: "",
  setProfileImage: (profileImage) => set({ profileImage: profileImage }),
  reset: () => set({ id: 0, token: "", role: "guest" }),
}));

export default useUserStore;
