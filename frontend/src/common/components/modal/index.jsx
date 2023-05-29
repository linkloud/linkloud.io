import { useEffect } from "react";

import { useBreakpoint } from "@/hooks/useBreakpoint";

import Portal from "../portal";
import Dimmed from "../dimmed";
import { XMarkIcon } from "@/static/svg";

const Modal = ({ isOpened, onCloseModal, children }) => {
  const { type } = useBreakpoint();

  useEffect(() => {
    document.body.style.overflow = isOpened ? "hidden" : "unset";

    return () => {
      document.body.style.overflow = "unset";
    };
  }, [isOpened]);

  return (
    <Portal elementId="modal-wrap">
      {isOpened && (
        <>
          <Dimmed />
          <div className="fixed flex justify-center items-center inset-0 z-[999] px-6">
            <section className="flex flex-col items-center pt-4 pb-10 px-7 w-full max-w-xl rounded-lg bg-white shadow-md">
              {/* header */}
              <div className="flex justify-end w-full">
                <button
                  type="button"
                  aria-label="닫기"
                  className="h-8 cursor-pointer"
                >
                  <XMarkIcon
                    onClick={onCloseModal}
                    className="m-auto stroke-gray-600 w-7 h-7"
                  />
                </button>
              </div>
              {children}
            </section>
          </div>
        </>
      )}
    </Portal>
  );
};

export default Modal;
