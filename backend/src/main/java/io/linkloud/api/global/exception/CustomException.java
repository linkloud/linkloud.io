package io.linkloud.api.global.exception;

import io.linkloud.api.global.exception.ExceptionCode.ExceptionCode;
import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}