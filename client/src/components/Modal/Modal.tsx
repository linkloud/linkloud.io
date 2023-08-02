import { useEffect } from "react";

import { XIcon } from "@/assets/svg";
import { StrictPropsWithChildren } from "@/types";

import { Dimmed } from "../Dimmed";
import { Portal } from "../Portal";

export interface ModalProps {
  name: string;
  isOpened: boolean;
  onClose: () => void;
}

export const Modal = ({
  name,
  isOpened,
  onClose,
  children,
}: StrictPropsWithChildren<ModalProps>) => {
  useEffect(() => {
    document.body.style.overflow = isOpened ? "hidden" : "unset";

    return () => {
      document.body.style.overflow = "unset";
    };
  }, [isOpened]);

  return (
    <Portal elementId="modal">
      <div className={isOpened ? "block" : "hidden"}>
        <Dimmed>
          <section className="fixed inset-0 sm:inset-auto sm:top-1/4 z-50 bg-white rounded-xl link-shadow-xl animate-fade-up animate-once animate-duration-300 animate-ease-out">
            <h1 className="sr-only">{name}</h1>

            <header className="flex justify-end pt-5 px-6">
              <button
                aria-label={`${name} 닫기`}
                type="button"
                className="flex justify-center items-center p-2"
                onClick={onClose}
              >
                <XIcon className="h-6 w-6 stroke-neutral-600" />
                <span className="sr-only">닫기</span>
              </button>
            </header>

            {children}
          </section>
        </Dimmed>
      </div>
    </Portal>
  );
};
