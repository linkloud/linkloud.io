package io.linkloud.api.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {

    // OAuth 소셜타입 구현체 클래스를 찾지 못할때
    INVALID_SOCIAL_TYPE(404, "Invalid Social Type requested"),

    // 유효하지 않은 토큰일 경우
    INVALID_TOKEN(401, "Invalid Token"),

    // 만료된 토큰
    EXPIRED_ACCESS_TOKEN(401, "Expired access token"),
    EXPIRED_REFRESH_TOKEN(401, "Expired refresh token");

    @Getter
    private int status;

    @Getter
    private String message;
}