import { useRef, useEffect } from "react";

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
