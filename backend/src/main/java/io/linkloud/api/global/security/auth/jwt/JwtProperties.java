package io.linkloud.api.global.security.auth.jwt;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

    @PostConstruct
    public void validate() {
        if (!StringUtils.hasText(secretKey)) {
            throw new IllegalArgumentException("JWT secretKey cannot be null or empty");
        }
    }
}