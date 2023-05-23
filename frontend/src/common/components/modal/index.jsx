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

  const responsiveClass =
    type === "xs"
      ? "w-full h-full"
      : "flex items-center justify-center  max-w-6xl";

  const className = `flex flex-col items-center pt-4 pb-7 px-7 max-w-6xl rounded-lg bg-white shadow-md `;

  return (
    <Portal elementId="modal-wrap">
      {isOpened && (
        <>
          <Dimmed />
          <div className="fixed flex justify-center items-center inset-0 z-[999]">
            <section className="flex flex-col items-center pt-4 pb-10 px-7 max-w-xl rounded-lg bg-white shadow-md">
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
