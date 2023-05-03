package io.linkloud.api.global.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
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
            .claim(MEMBER_ID_CLAIM,memberID)
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
            throw new JwtException("만료된 JWT 토큰입니다", e);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다: {}", e.getMessage());
            throw new JwtException("지원되지 않는 JWT 토큰입니다", e);
        } catch (MalformedJwtException e) {
            log.error("잘못된 형식의 JWT 토큰입니다: {}", e.getMessage());
            throw new JwtException("잘못된 형식의 JWT 토큰입니다", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 null 이거나 빈 문자열입니다: {}", e.getMessage());
            throw new JwtException("JWT 토큰이 null 이거나 빈 문자열입니다", e);
        }
    }

    /**
     * accessToken 으로 MemberId 추출
     * @param accessToken 액세스 토큰
     * @return memberId
     */
    public Long extractMemberIdByAccessToken(String accessToken) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(accessToken)
            .getBody();
        return claims.get(MEMBER_ID_CLAIM, Long.class);
    }


}
