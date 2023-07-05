import { useState, useRef, useEffect, DependencyList } from "react";

interface Return<T> {
  ref: React.RefObject<T>;
}

function useScrollToElement<T extends HTMLElement>(
  dependencies: DependencyList
): Return<T> {
  const ref = useRef<T>(null);
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    if (isMounted && ref.current) {
      ref.current.scrollIntoView({ behavior: "smooth" });
    }
  }, dependencies);

  useEffect(() => {
    setIsMounted(true);
  }, []);

  return { ref };
}

export default useScrollToElement;
