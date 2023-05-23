package io.linkloud.api.global.security.resolver;

import io.jsonwebtoken.Claims;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import io.linkloud.api.global.security.auth.jwt.utils.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) {

        String accessToken = getTokenFromRequest(webRequest);
        if (!jwtProvider.validateAccessToken(accessToken)) {
            throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
        }

        return Long.valueOf(jwtProvider.getClaims(accessToken, Claims::getId));
    }


    private String getTokenFromRequest(NativeWebRequest webRequest) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return HeaderUtil.getAccessToken(request);
    }

}
