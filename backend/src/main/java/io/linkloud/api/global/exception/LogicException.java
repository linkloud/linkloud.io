package io.linkloud.api.global.exception;

import lombok.Getter;

public class LogicException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public LogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}