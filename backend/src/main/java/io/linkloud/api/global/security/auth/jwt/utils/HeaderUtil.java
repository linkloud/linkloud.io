package io.linkloud.api.global.security.auth.jwt.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HeaderUtil {

    private final String accessHeaderAuthorization;
    private final String refreshHeaderAuthorization;
    private final static String TOKEN_PREFIX = "Bearer ";

    public HeaderUtil(
        @Value("${jwt.accessHeader}") String accessHeaderAuthorization,
        @Value("${jwt.refreshHeader}") String refreshHeaderAuthorization) {
        this.accessHeaderAuthorization = accessHeaderAuthorization;
        this.refreshHeaderAuthorization = refreshHeaderAuthorization;
    }

    public String extractAccessToken(HttpServletRequest request) {
        return extractToken(request, accessHeaderAuthorization);
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return extractToken(request, refreshHeaderAuthorization);
    }

    private String extractToken(HttpServletRequest request, String headerName) {
        String headerValue = request.getHeader(headerName);
        if (headerValue == null) {
            return null;
        }
        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}

