package io.linkloud.api.global.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
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


    /**
     * memberID 기준으로 AccessToken 을 생성
     */
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

    /**
     * AccessToken 을 생성
     * 보안상 사용자의 memberID 를 이용하지않고,
     * 랜덤 값인 UUID 를 이용해 claim 으로 저장
     */
    public String generateRefreshToken() {
        UUID uuid = UUID.randomUUID();
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(refreshTokenExpiration);

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .claim("refreshTokenId", uuid.toString())
            .setExpiration(Date.from(expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }


}
