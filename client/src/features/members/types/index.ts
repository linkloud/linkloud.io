export interface Member {
  id: number;
  nickname: string;
  picture: string;
  role: Role;
}

export type Role = "USER" | "NEW_MEMBER" | "MEMBER" | "ADMIN";
