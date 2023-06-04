package io.linkloud.api.global.security.auth.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.global.exception.CustomException;
import java.time.Instant;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(JwtProviderTest.Config.class)
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;


    @DisplayName("테스트용 jwt 설정 환경변수")
    @Test
    public void checkTestConfig() {
        jwtProvider.getRefreshTokenExpiration();
        Assertions.assertThat(jwtProvider.getRefreshTokenExpiration()).isEqualTo(100000L);
        Assertions.assertThat(jwtProvider.getAccessTokenExpiration()).isEqualTo(9999);
    }

    @TestConfiguration
    static class Config {

        @Bean
        public JwtProperties jwtProperties() {
            final JwtProperties properties = new JwtProperties();
            properties.setSecretKey("test secretKey.fdsafsadfdsadfsfdsfdsfdsfdsfdasfdsfdsfdsdsfafkldlakfdjafdjafdaafsfdas");
            properties.setAccessTokenExpiration(9999L);
            properties.setRefreshTokenExpiration(100000L);
            return properties;
        }

        @Bean
        public JwtProvider jwtProvider(final JwtProperties jwtProperties) {
            return new JwtProvider(jwtProperties);
        }
    }

    /**
     * 액세스 토큰 START
     */
    @DisplayName("액세스 토큰 발급 성공")
    @Test
    public void accessToken_success() {
        // given
        Long memberId = 1L;
        SocialType socialType = SocialType.google;

        // when
        String accessToken = jwtProvider.generateAccessToken(memberId, socialType);

        // then
        assertThat(accessToken).isNotBlank();
        assertThat(
            jwtProvider.getClaims(accessToken, JwtTokenType.ACCESS_TOKEN, Claims::getId)).isEqualTo(
            memberId.toString());
    }


    @DisplayName("검증실패 - 유효하지 않은 액세스 토큰 ")
    @Test
    void accessToken_invalid() {
        String invalidToken = "invalidToken";

        assertThrows(CustomException.class, () -> {
            jwtProvider.validateToken(invalidToken, JwtTokenType.ACCESS_TOKEN);
        });
        CustomException e = assertThrows(CustomException.class,
            () -> jwtProvider.validateToken(invalidToken, JwtTokenType.ACCESS_TOKEN));
        assertThat(e.getMessage()).isEqualTo("Invalid Token");
    }

    @DisplayName("검증실패 - 만료된 액세스 토큰")
    @Test
    void accessToken_expired() {
        String expiredToken = createExpiredToken();

        assertThrows(CustomException.class, () -> {
            jwtProvider.validateToken(expiredToken, JwtTokenType.ACCESS_TOKEN);
        });

        CustomException e = assertThrows(CustomException.class,
            () -> jwtProvider.validateToken(expiredToken, JwtTokenType.ACCESS_TOKEN));
        assertThat(e.getMessage()).isEqualTo("Expired access token");
    }

    @DisplayName("검증실패 - empty 액세스 토큰")
    @Test
    void accessToken_empty() {
        String empty = "";

        assertThrows(CustomException.class, () -> {
            jwtProvider.validateToken(empty, JwtTokenType.ACCESS_TOKEN);
        });

        CustomException e = assertThrows(CustomException.class,
            () -> jwtProvider.validateToken(empty, JwtTokenType.ACCESS_TOKEN));
        assertThat(e.getMessage()).isEqualTo("Invalid Token");

    }

    @DisplayName("검증실패 - null 액세스 토큰")
    @Test
    void accessToken_null() {

        assertThrows(CustomException.class, () -> {
            jwtProvider.validateToken(null, JwtTokenType.ACCESS_TOKEN);
        });

        CustomException e = assertThrows(CustomException.class,
            () -> jwtProvider.validateToken(null, JwtTokenType.ACCESS_TOKEN));
        assertThat(e.getMessage()).isEqualTo("Invalid Token");

    }


    /**
     * 액세스 토큰 END
     */


    /**
     * 리프레시 토큰 START
     */

    @DisplayName("리프레시 토큰 발급 성공")
    @Test
    public void refreshToken_success() {
        // given
        Long memberId = 1L;

        // when
        String refreshToken = jwtProvider.generateRefreshToken(memberId);

        // then
        assertThat(refreshToken).isNotBlank();
        assertThat(
            jwtProvider.getClaims(refreshToken, JwtTokenType.ACCESS_TOKEN, Claims::getId)).isEqualTo(
            memberId.toString());
    }

    @DisplayName("검증실패 - 유효하지 않은 리프레시 토큰 ")
    @Test
    void refreshToken_invalid() {
        String invalidToken = "invalidToken";

        assertThrows(CustomException.class, () -> {
            jwtProvider.validateToken(invalidToken, JwtTokenType.REFRESH_TOKEN);
        });
        CustomException e = assertThrows(CustomException.class,
            () -> jwtProvider.validateToken(invalidToken, JwtTokenType.REFRESH_TOKEN));
        assertThat(e.getMessage()).isEqualTo("Invalid Token");
    }

    @DisplayName("검증실패 - 만료된 리프레시 토큰")
    @Test
    void refreshToken_expired() {
        String expiredToken = createExpiredToken();

        assertThrows(CustomException.class, () -> {
            jwtProvider.validateToken(expiredToken, JwtTokenType.REFRESH_TOKEN);
        });

        CustomException e = assertThrows(CustomException.class,
            () -> jwtProvider.validateToken(expiredToken, JwtTokenType.REFRESH_TOKEN));

        assertThat(e.getMessage()).isEqualTo("Expired refresh token");
    }
    /**
     * 리프레시 토큰 END
     */

    // 만료된 토큰 생성
    private String createExpiredToken() {
        Instant now = Instant.now();
        Instant expiration = now.minusSeconds(10);  // 현재 - 10초전

        return Jwts.builder()
            .setIssuer("linkloud")
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expiration))
            .signWith(jwtProvider.getSecretKey())
            .compact();
    }

}