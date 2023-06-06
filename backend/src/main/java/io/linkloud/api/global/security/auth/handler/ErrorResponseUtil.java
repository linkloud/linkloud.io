package io.linkloud.api.global.security.auth.handler;

import com.google.gson.Gson;
import io.linkloud.api.global.common.ErrorResponse;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.ExceptionCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ErrorResponseUtil {

    /**
     * 사용자 권한이 없을 때 (MemberAccessDeniedHandler.class)
     */
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

    /**
     * jwtFilter 에서 걸렸을 때
     */
    public static void sendErrorResponse(HttpServletResponse response, CustomException e) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(e.getExceptionCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(e.getExceptionCode().getStatus());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

}
