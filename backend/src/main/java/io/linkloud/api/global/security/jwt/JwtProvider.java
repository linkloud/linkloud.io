package io.linkloud.api.global.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    //    private final Key secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    private final Key secretKey;
    private static final String MEMBER_ID_CLAIM = "memberId";
    private static final String REFRESH_TOKEN_ID_CLAIM = "refreshTokenId";

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    // TODO : 권한 넘겨야 됨
    /**
     * AccessToken 생성
     * memberId 기반
     */
    public String generateAccessToken(Long memberId) {
        log.info("액세스토큰 생성");
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .claim(MEMBER_ID_CLAIM,memberId)
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
        UUID uuid = UUID.randomUUID(); // TODO : Member 테이블 refreshToken
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .claim(REFRESH_TOKEN_ID_CLAIM, uuid.toString())
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
             return claimsJws.getBody().get(MEMBER_ID_CLAIM, Long.class);
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
            return claimsJws.getBody().get(REFRESH_TOKEN_ID_CLAIM, String.class);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token", e);
        }
    }


}
