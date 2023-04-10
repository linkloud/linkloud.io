package io.linkloud.api.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionCode {
    TEMPORARY_ERROR(400, "Temporary ERROR"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_UNAUTHORIZED(401, "Member Unauthorized"),
    MEMBER_NOT_MATCH(403, "Member Not Matched"),
    MEMBER_ALREADY_EXISTS(409, "Member Already Exist.");

    @Getter
    private int status;

    @Getter
    private String message;
}
