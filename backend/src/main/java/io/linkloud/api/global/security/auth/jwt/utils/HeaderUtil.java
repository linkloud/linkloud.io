package io.linkloud.api.global.security.auth.jwt.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
