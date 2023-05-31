package io.linkloud.api.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {

    // OAuth 소셜타입 구현체 클래스를 찾지 못할때
    INVALID_SOCIAL_TYPE(404, "Invalid Social Type requested"),

    // Redis 에서 refreshToken 을 찾지 못했을 때
    REFRESH_TOKEN_NOT_FOUND(400, "Refresh token not found"),

    // 유효하지 않은 토큰일 경우
    INVALID_TOKEN(401, "Invalid Token");
    @Getter
    private int status;

    @Getter
    private String message;
}