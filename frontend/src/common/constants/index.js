export const ROLE = {
  USER: "USER",
  NEW_MEMBER: "NEW_MEMBER",
  MEMBER: "MEMBER",
  ADMIN: "ADMIN",
};

export const TAG_SORT_OPTIONS = {
  POPULARITY: "popularity", // 인기순
  LATEST: "latest", // 최신순
  NAME: "name", // 이름순
};

export const ROUTES_PATH = {
  HOME: "/",
  SEARCH: "/search",

  MEMBER_PROFILE: "/profile",

  LINK_REG: "/links/reg",

  TAGS_LIST: "/tags",
};

export const ERROR_CODE = {
  // 인증
  UNAUTH: "User unauthorized",
  REFRESH_EXPIRED_TOKEN: "Expired refresh token",
  REFRESH_EXPIRED_COOKIE:
    "Required cookie 'refreshToken' for method parameter type String is not present",
  ACCESS_EXPIRED_TOKEN: "Expired access token",

  // 서버
  SERVER_ERROR: "Request failed with status code 500",
};
