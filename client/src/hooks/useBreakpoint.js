import { useState, useEffect } from "react";

export function useBreakpoint() {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  const onResize = () => setWindowWidth(window.innerWidth);

  useEffect(() => {
    window.addEventListener("resize", onResize);
    return () => window.removeEventListener("resize", onResize);
  }, []);

  const type = (() => {
    if (windowWidth < 768) return "xs";
    if (windowWidth >= 768 && windowWidth < 1440) return "md";
    if (windowWidth >= 1440) return "lg";
    return null;
  })();

  const width = windowWidth;

  return { width, type };
}
