import { Tag } from "@/features/tags/types";
import { BaseTimeEntity } from "@/types";

export interface Article extends BaseTimeEntity {
  id: number;
  memberId: number;
  title: string;
  url: string;
  description: string;
  views: number;
  bookmarks: number;
  tags: Tag[];
}
