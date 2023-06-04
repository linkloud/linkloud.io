package io.linkloud.api.global.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
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


    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * access 토큰 생성
     * @param memberID (claim 에 담을 회원 정보)
     * @param socialType 회원 OAuth 소셜 타입
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

    /**
     * refresh 토큰 생성
     * @param memberId (claim 에 담을 회원 정보)
     * @return JwtAccessToken
     */
    public String generateRefreshToken(Long memberId) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(getRefreshTokenExpiration());
        UUID uuid = UUID.randomUUID();
        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .setId(String.valueOf(memberId))
            .setSubject(uuid.toString())
            .setExpiration(Date.from(expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }


    /**
     * token 을 검증하고, 정상적인 토큰일 경우에만 해당 클레임 리턴(ID,Expiration,,,)
     * @param token 토큰 값(aaaa.bbbbb.ccccc)
     * @param tokenType 액세스토큰,리프레시토큰
     * @param <R> Claims 객체를 입력받아 'R' 타입의 결과값으로 반환 (ID면 Long 타입 반환)
     * @return Claims 에서 추출한 R 타입의 결과 값
     */
    public <R> R getClaims(String token, JwtTokenType tokenType, Function<Claims, R> claimsResolver) {
        validateToken(token, tokenType);
        final Claims claims = extractClaimsFromJwt(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰 검증
     * @param token 토큰 값(aaaa.bbbbb.ccccc)
     * @param tokenType 액세스토큰,리프레시토큰
     * @return 정상적인 토큰 true, 비정상적인 토큰 예외처리 로직 실행
     */
    public boolean validateToken(String token, JwtTokenType tokenType) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            log.error("이 토큰은({}) 만료된 토큰입니다={}", tokenType.name(), e.getMessage());
            throw new CustomException(tokenType == JwtTokenType.ACCESS_TOKEN ?
                AuthExceptionCode.EXPIRED_ACCESS_TOKEN : // JwtTokenType 이 액세스토큰 일경우(참)
                AuthExceptionCode.EXPIRED_REFRESH_TOKEN); // JwtTokenType 이 액세스토큰이 아닐경우(거짓)
        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 ({})토큰입니다={}",tokenType,e.getMessage());
            throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
        }
        return true;
    }

    private Claims extractClaimsFromJwt(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // 리프레시 토큰 만료시간 리턴
    public long getRefreshTokenExpiration() {
        return jwtProperties.getRefreshTokenExpiration();
    }
}
