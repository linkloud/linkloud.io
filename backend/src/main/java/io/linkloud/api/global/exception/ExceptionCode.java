package io.linkloud.api.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionCode {
    TEMPORARY_ERROR(400, "Temporary ERROR"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_UNAUTHORIZED(401, "Member Unauthorized"),
    MEMBER_NOT_MATCH(403, "Member Not Matched"),
    MEMBER_ALREADY_EXISTS(409, "Member Already Exist."),
    JSON_REQUEST_FAILED(400, "Json Request Failed."),
    INVALID_SOCIAL_TYPE(404, "Invalid Social Type requested"); // OAuth 액세스토큰 요청 구현체 클래스를 찾지 못할때



    @Getter
    private int status;

    @Getter
    private String message;
}
