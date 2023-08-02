import { Tag } from "@/features/tags/types";
import { BaseTimeEntity } from "@/types";

export interface Article extends BaseTimeEntity {
  id: number;
  memberId: number;
  title: string;
  url: string;
  description: string;
  readStatus: ReadStatus;
  views: number;
  bookmarks: number;
  tags: Tag[];
}

export type ReadStatus = "UNREAD" | "READING" | "READ";
