import { BaseTimeEntity } from "@/types";

export interface Tag extends Pick<BaseTimeEntity, "createdAt"> {
  id: number;
  name: string;
  count?: number;
}

export type TagSort = "popularity" | "latest" | "name";
