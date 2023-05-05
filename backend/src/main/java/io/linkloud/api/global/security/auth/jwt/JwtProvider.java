package io.linkloud.api.global.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import io.linkloud.api.global.security.auth.jwt.utils.JwtProperties;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final Key secretKey;

    private static final String MEMBER_ID_CLAIM = "memberId";


    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * access 토큰 생성
     * @param memberId (id 에 담을 회원 정보)
     * @param socialType 소셜타입
     * @return JwtAccessToken
     */
    public String generateAccessToken(Long memberId, SocialType socialType) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpiration());
        return Jwts.builder()
            .setIssuedAt(Date.from(now))
            .setAudience(socialType.name())
            .setId(String.valueOf(memberId))
            .setExpiration(Date.from(expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * refresh 토큰 생성
     *  @param memberID (id 에 담을 회원 정보)
     *  @return JwtRefreshToken
     */
    public String generateRefreshToken(Long memberID) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpiration());
        return Jwts.builder()
            .setIssuedAt(Date.from(now))
            .setId(String.valueOf(memberID))
            .setExpiration(Date.from(expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * access 토큰 검증
     * @param accessToken 액세스 토큰
     * @return 정상적인 토큰 true 리턴
     */
    public boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken);
            Instant now = Instant.now();
            Date expiration = claims.getBody().getExpiration();
            return expiration.toInstant().isAfter(now);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다: {}", e.getMessage());
            throw new LogicException(ExceptionCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다: {}", e.getMessage());
            throw new LogicException(ExceptionCode.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            log.error("잘못된 형식의 JWT 토큰입니다: {}", e.getMessage());
            throw new LogicException(ExceptionCode.MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 null 이거나 빈 문자열입니다: {}", e.getMessage());
            throw new LogicException(ExceptionCode.INVALID_TOKEN);
        }
    }


    /**
     * 전달된 JWT 토큰을 파싱하여 Claims 객체를 얻고,
     * Claims 객체를 함수형 인터페이스 Function<Claims, R>에 전달하여 R 타입의 값을 반환
     * @param token 토큰
     * @param <R> Claims 객체를 입력받아 'R' 타입의 결과값으로 반환
     * @return Claims 에서 추출한 R 타입의 결과 값
     */
    public <R> R getClaims(String token, Function<Claims, R> claimsResolver) {
        final Claims claims = parseClaimsJwt(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseClaimsJwt(final String accessToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (IllegalArgumentException e) {
            throw new LogicException(ExceptionCode.INVALID_TOKEN);
        }
    }
}
