package io.linkloud.api.global.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.header}")
    private String accessHeader;

    /**
     * 메모리 노출
     * private SecretKey key =  Keys.secretKeyFor(SignatureAlgorithm.HS256); 로 변경
    @Deprecated
    @Value("${jwt.secretKey}")
    private String secretKey;
     *
     */
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.accessTokenExpiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiration;

    @Value("${jwt.refresh-header}")
    private String refreshHeader;

    private final String BEARER = "Bearer";



    public String generateAccessToken(Long memberID) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(accessTokenExpiration);

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .claim("memberId",memberID)
            .setExpiration(Date.from(expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

}
