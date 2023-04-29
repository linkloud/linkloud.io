import { encrypt, decrypt } from "../crypto";

/** 3days */
const DEFAULT_EXPIRATION_TIME = 60 * 60 * 24 * 3 * 1000;

export const setLocal = (key, value, expire = DEFAULT_EXPIRATION_TIME) => {
  const data = encrypt({
    value,
    expire: expire !== null ? Date.now() + expire * 1000 : null,
  });
  localStorage.setItem(key, data);
};

export const getLocal = (key) => {
  const data = localStorage.getItem(key);
  if (!data) return null;

  let storageData;

  try {
    storageData = decrypt(data);
  } catch {
    return null;
  }

  if (!storageData) return null;
  if (storageData.expire === null || storageData.expire >= Date.now()) {
    return storageData.value;
  }
  return null;
};

export function removeLocal(key) {
  localStorage.removeItem(key);
}

export function clearLocal() {
  localStorage.clear();
}
