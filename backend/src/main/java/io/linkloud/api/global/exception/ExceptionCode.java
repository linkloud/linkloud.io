package io.linkloud.api.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    TEMPORARY_ERROR(400, "Temporary ERROR"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_UNAUTHORIZED(401, "Member Unauthorized"),
    MEMBER_NOT_MATCH(403, "Member Not Match"),
    MEMBER_EMAIL_EXISTS(409, "Email Already Exist."),
    MEMBER_NAME_EXISTS(409, "Name Already Exist.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
