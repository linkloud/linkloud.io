import { useRef, useEffect } from "react";

/**
 * dependencies에 변화가 일어나면 ref로 scroll을 이동
 */
const useScrollToView = (dependencies = []) => {
  const ref = useRef(null);

  useEffect(() => {
    if (ref.current) {
      ref.current.scrollIntoView({ behavior: "smooth" });
    }
  }, dependencies);

  return { ref };
};

export default useScrollToView;
