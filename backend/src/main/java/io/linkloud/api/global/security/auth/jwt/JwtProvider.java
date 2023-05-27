package io.linkloud.api.global.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.CustomException;
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
     * @param memberID (claim 에 담을 회원 정보)
     * @param socialType
     * @return JwtAccessToken
     */
    public String generateAccessToken(Long memberID, SocialType socialType) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpiration());
        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .setAudience(socialType.name())
            .setId(String.valueOf(memberID))
            .setExpiration(Date.from(expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /** todo : refreshToken 정보에 memberId 가 들어감, 보안상 좋지않음
     * refresh 토큰 생성
     * @param memberId (claim 에 담을 회원 정보)
     * @return JwtAccessToken
     */
    public String generateRefreshToken(Long memberId) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .setId(String.valueOf(memberId))
            .setExpiration(Date.from(expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * 클라이언트에게 너무 자세한 정보를 보여줘서 더 이상 사용하지 않음
     */
    @Deprecated
    public boolean validateAccessToken_deprecated(String accessToken) {
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
            throw new CustomException(AuthExceptionCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다: {}", e.getMessage());
            throw new CustomException(AuthExceptionCode.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            log.error("잘못된 형식의 JWT 토큰입니다: {}", e.getMessage());
            throw new CustomException(AuthExceptionCode.MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 null 이거나 빈 문자열입니다: {}", e.getMessage());
            throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
        } catch (SignatureException e) {
            log.error("유효하지않은 토큰입니다: {}", e.getMessage());
            throw new CustomException(AuthExceptionCode.USER_UNAUTHORIZED);
        }
    }

    /**
     * token 토큰 검증
     * @param token 액세스 토큰
     * @return 정상적인 토큰 true 리턴
     */
    public boolean validateAccessToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            Instant now = Instant.now();
            Date expiration = claims.getBody().getExpiration();
            return expiration.toInstant().isAfter(now);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 토큰 검증 중 오류 발생: {}", e.getMessage());
            throw new CustomException(AuthExceptionCode.USER_UNAUTHORIZED);
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

    private Claims parseClaimsJwt(final String token) {
        if (validateAccessToken(token)) {
            return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        }
        throw new CustomException(AuthExceptionCode.USER_UNAUTHORIZED);
    }
}
