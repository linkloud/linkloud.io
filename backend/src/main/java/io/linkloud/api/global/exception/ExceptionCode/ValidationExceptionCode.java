package io.linkloud.api.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ValidationExceptionCode implements ExceptionCode {

    INVALID_LOCATION(400, "Invalid location value"),
    INVALID_STATE(400, "Invalid state value"),
    ORDER_BY_NOT_VALID(400, "Not valid orderBy value"),
    TARGET_TYPE_BY_NOT_VALID(400, "Not valid targetType value");

    @Getter
    private int status;

    @Getter
    private String message;
}
