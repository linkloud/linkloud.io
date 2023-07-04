import ReactDOM from "react-dom";

import { StrictPropsWithChildren } from "@/types";

export interface PortalProps {
  elementId: string;
}

export const Portal = ({
  elementId,
  children,
}: StrictPropsWithChildren<PortalProps>) => {
  const potal = document.getElementById(elementId);
  return potal && ReactDOM.createPortal(children, potal);
};
