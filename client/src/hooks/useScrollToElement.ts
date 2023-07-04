import { useRef, useEffect, DependencyList } from "react";

interface Return<T> {
  ref: React.RefObject<T>;
}

function useScrollToElement<T extends HTMLElement>(
  dependencies: DependencyList
): Return<T> {
  const ref = useRef<T>(null);

  useEffect(() => {
    if (ref.current) {
      ref.current.scrollIntoView({ behavior: "smooth" });
    }
  }, dependencies);

  return { ref };
}

export default useScrollToElement;
