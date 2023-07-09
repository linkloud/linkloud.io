package io.linkloud.api.global.advice;


import io.linkloud.api.domain.discord.service.DiscordWebhookService;
import io.linkloud.api.global.common.ErrorResponse;
import io.linkloud.api.global.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


    private final DiscordWebhookService discordWebhookService;

    // 요청 바디 필드 유효성 검증 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {

        return ErrorResponse.of(e.getBindingResult());
    }

    // 경로 변수 유효성 검증 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {

        return ErrorResponse.of(e.getConstraintViolations());
    }

    // converter가 변환하지 못했을 경우(enum type에 존재하지 않는 값)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getPropertyName() + " Type Mismatched");
    }

    // 서버 로직 내 예외 처리
    @ExceptionHandler
    public ResponseEntity handleLogicException(CustomException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }

    // url에 대해 지원하지 않는 http method 일 때 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }

    // HTTP Body를 제대로 파싱하지 못했을 때 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST,
                "Required request body is missing");
    }

    // 요청 시 쿼리 파라미터가 결여됐을 때 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        return ErrorResponse.of(HttpStatus.BAD_REQUEST,
                e.getMessage());
    }


    // 요청 쿠키가 null 값이거나 빈 값일 경우
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleMissingRequestCookieException(MissingRequestCookieException e) {
        return ErrorResponse.of(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    // 위에서 지정한 예외 외의 서버 로직 예외에 대한 예외 처리.
    // 예상하지 못한 서버 예외
    // 운영에 치명적일 수 있음.
    // 반드시 로그를 기록하고, 관리자에게 알림을 줄 것.
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(HttpServletRequest req,Exception e) {
        log.error("# handle Exception", e);
        discordWebhookService.sendMessage(e,req);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
