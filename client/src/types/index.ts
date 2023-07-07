import { ReactNode } from "react";

export type StrictPropsWithChildren<P = unknown> = P & {
  children: ReactNode;
};

// entity
export interface BaseTimeEntity {
  createdAt: string;
  modifiedAt: string;
}

// api
export interface SingleDataResponse<T> {
  data: T;
}

export interface MultiDataResponse<T> extends SingleDataResponse<T> {
  pageInfo: PageInfo;
}

export interface PageInfo {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
