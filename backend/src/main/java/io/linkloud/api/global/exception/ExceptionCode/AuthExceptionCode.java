package io.linkloud.api.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    TOKEN_NOT_FOUND(400, "Refresh token not found"),
    ACCESS_TOKEN_INVALID_01(401, "Invalid Access token"),
    REFRESH_TOKEN_INVALID(401, "Invalid Refresh token"),
    ACCESS_TOKEN_EXPIRED(401, "Access token expired"),
    REFRESH_TOKEN_EXPIRED(401, "Refresh token expired"),
    AUTHORIZED_FAIL(403, "Authorized Fail"),
    USER_UNAUTHORIZED(403, "User unauthorized"),

    // OAuth 액세스토큰 요청 구현체 클래스를 찾지 못할때
    INVALID_SOCIAL_TYPE(404, "Invalid Social Type requested."),

    /** JWT Exception Code Start **/
    INVALID_TOKEN(400, "Jwt token is null or empty or only whitespace."),
    EXPIRED_TOKEN(401, "Expired JWT token."),
    UNSUPPORTED_TOKEN(400, "Unsupported JWT token."),
    MALFORMED_TOKEN(400, "Malformed JWT token.");
    /** JWT Exception Code End **/

    @Getter
    private int status;

    @Getter
    private String message;
}