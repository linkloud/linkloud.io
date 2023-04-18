package io.linkloud.api.global.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final Key secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());


    /**
     * AccessToken 생성
     * memberID 기반
     */
    public String generateAccessToken(Long memberID) {
        log.info("액세스토큰 생성");
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .claim("memberId",memberID)
            .setExpiration(Date.from(expiration))
            .signWith(secretKey)
            .compact();
    }

    /**
     * RefreshToken 생성
     * 보안상 사용자의 memberID 를 이용하지않고,
     * 랜덤 값인 UUID 를 이용해 claim 으로 저장
     * @return refreshToken
     */
    public String generateRefreshToken() {
        log.info("리프레시 토큰 생성");
        UUID uuid = UUID.randomUUID();
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .claim("refreshTokenId", uuid.toString())
            .setExpiration(Date.from(expiration))
            .signWith(secretKey)
            .compact();
    }

    /**
     * AccessToken 검증하고, 복호화한다음에 memberId 를 리턴한다
     * @param accessToken JWT 토큰
     * @return memberID
     * @throws JwtException 검증 실패시 예외 발생
     */
     public Long validateAccessToken(String accessToken) throws JwtException{
         try{
             log.info("액세스 토큰 검증로직 시작");
             Jws<Claims> claimsJws = Jwts.parserBuilder()
                 .setSigningKey(secretKey)
                 .build()
                 .parseClaimsJws(accessToken);
             return claimsJws.getBody().get("memberId", Long.class);
         } catch (JwtException | IllegalArgumentException e) {
             log.error("Invalid JWT token: {}", e.getMessage());
             throw new JwtException("Invalid JWT token", e);
         }
    }

    /**
     * RefreshToken 검증하고, 복호화한다음에 refreshTokenId 를 리턴한다
     * @param refreshToken JWT 토큰
     * @return refreshTokenId
     * @throws JwtException 검증 실패시 예외 발생
     */
    public String validateRefreshToken(String refreshToken) throws JwtException {
        try {
            log.info("리프레시 토큰 검증로직 시작");
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken);
            return claimsJws.getBody().get("refreshTokenId", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token", e);
        }
    }


}
