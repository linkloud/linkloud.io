import Portal from "../portal";
import Dimmed from "../dimmed";

import { XMarkIcon } from "@/static/svg";

import { useBreakpoint } from "@/hooks/useBreakpoint";

const Modal = ({ isOpened, onCloseModal, children }) => {
  const { type } = useBreakpoint();

  const responsiveClass =
    type === "xs"
      ? "w-auto top-auto h-full"
      : "top-1/4 border-solid border-gray-300 shadow-md rounded-lg";

  const className = `${responsiveClass} flex flex-col p-9 bg-white z-[1000] max-w-6xl`;

  return (
    <Portal elementId="modal-wrap">
      {isOpened && (
        <>
          <Dimmed onClick={onCloseModal}></Dimmed>
          <dialog className={className}>
            <div className="flex justify-end">
              <XMarkIcon
                onClick={onCloseModal}
                className="block md:hidden stroke-gray-600 w-10 h-10 cursor-pointer"
              />
            </div>
            {children}
          </dialog>
        </>
      )}
    </Portal>
  );
};

export default Modal;
