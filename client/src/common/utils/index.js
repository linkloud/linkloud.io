export * from "./crypto";
export * from "./storage";

/** 개발 환경에서만 출력되는 로그 */
export const log = (message) => {
  if (import.meta.env.DEV) console.log(message);
};

/**
 * `delay`마다 한번 함수를 실행하는 새로운 함수를 반환한다.
 * @param {Function} func
 * @param {number} delay
 * @returns
 */
export function throttle(func, delay) {
  let wait = false;
  return (...args) => {
    if (wait) {
      return;
    }

    func(...args);
    wait = true;
    setTimeout(() => {
      wait = false;
    }, delay);
  };
}
