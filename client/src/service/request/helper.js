import { ERROR_CODE } from "@/common/constants";

export const isServerError = (error) => {
  // server exception
  const hasServerErrorStatus =
    error.status && (error.status === 500 || error.status === "500");

  // axios exception
  const hasServerErrorMessage = error.message === ERROR_CODE.SERVER_ERROR;

  return hasServerErrorStatus || hasServerErrorMessage;
};
