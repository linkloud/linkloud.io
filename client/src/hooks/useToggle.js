import { useState, useCallback } from "react";

function useToggle(initialValue = false) {
  const [value, setValue] = useState(initialValue);

  const toggle = useCallback(() => {
    setValue((v) => !v);
  }, []);

  return { value, toggle };
}

export default useToggle;
