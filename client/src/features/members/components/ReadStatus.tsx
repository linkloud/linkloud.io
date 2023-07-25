import { ReadStatus as Status } from "@/features/articles/types";

import { DotBadge } from "@/components/Badge";

export interface ReadStatusProps {
  status: Status;
}

export const ReadStatus = ({ status }: ReadStatusProps) => {
  if (status === "UNREAD" || !status) return null;

  return <DotBadge styleName={status === "READ" ? "success" : "primary"} />;
};
