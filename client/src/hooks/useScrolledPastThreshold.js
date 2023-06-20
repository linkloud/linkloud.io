import { useState, useEffect } from "react";

import { throttle } from "@/common/utils";

/**
 * 스크롤이 thresholdY 값을 지났는지 확인하는 hook
 * 스크롤 최상단은 0부터 시작함
 */
function useScrolledPastThreshold(thresholdY = 100, delay = 50) {
  const [isScrollTop, setIsScrollTop] = useState(true);

  useEffect(() => {
    document.addEventListener("scroll", throttledHandleScroll);
    return () => {
      document.removeEventListener("scroll", throttledHandleScroll);
    };
  }, [isScrollTop]);

  const throttledHandleScroll = throttle(() => {
    if (window.scrollY > 30) {
      setIsScrollTop(false);
    } else if (window.scrollY <= 30) {
      setIsScrollTop(true);
    }
  }, 50);

  return isScrollTop;
}

export default useScrolledPastThreshold;
