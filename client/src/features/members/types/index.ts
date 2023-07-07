export interface Member {
  nickname: string;
  picture: string;
  role: Role;
}

export type Role = "USER" | "NEW_MEMBER" | "MEMBER" | "ADMIN";
