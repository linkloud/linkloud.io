package io.linkloud.api.global.security.resolver;

import io.jsonwebtoken.Claims;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import io.linkloud.api.global.security.auth.jwt.JwtTokenType;
import io.linkloud.api.global.security.auth.jwt.utils.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberId.class)
            && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(
        @NonNull MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        @NonNull NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) {

        String accessToken = getTokenFromRequest(webRequest);
//        if (!jwtProvider.validateToken(accessToken,JwtTokenType.ACCESS_TOKEN)) {
//            throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
//        }

       // accessToken 이 없는 경우
        if (accessToken == null || accessToken.isEmpty()) {
            if (Objects.requireNonNull(parameter.getParameterAnnotation(LoginMemberId.class)).required()) {
                throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
            } else{
                return null;
            }
        }

        // 토큰이 유효하지 않은 경우
        if (!jwtProvider.validateToken(accessToken, JwtTokenType.ACCESS_TOKEN)) {
            if (Objects.requireNonNull(parameter.getParameterAnnotation(LoginMemberId.class)).required()) {
                throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
            } else {
                return null;
            }
        }


        return Long.valueOf(jwtProvider.getClaims(accessToken, JwtTokenType.REFRESH_TOKEN, Claims::getId));
    }


    private String getTokenFromRequest(NativeWebRequest webRequest) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return HeaderUtil.getAccessToken(request);
    }

}
