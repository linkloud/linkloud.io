import CryptoJS from "crypto-js";

const key = import.meta.env.VITE_CRYPTO_KEY;

/**
 * @returns encrypted JSON string
 */
export const encrypt = (data) => {
  const res = JSON.stringify(data);
  return CryptoJS.AES.encrypt(res, key).toString();
};

/**
 * @returns JSON object {}
 */
export const decrypt = (encrypted) => {
  const decrypted = CryptoJS.AES.decrypt(encrypted, key);
  const data = decrypted.toString(CryptoJS.enc.Utf8);
  if (!data) {
    return null;
  }
  return JSON.parse(data);
};
