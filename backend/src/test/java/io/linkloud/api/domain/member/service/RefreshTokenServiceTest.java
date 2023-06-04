package io.linkloud.api.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import io.linkloud.api.domain.member.dto.CreateRefreshTokenRequestDto;
import io.linkloud.api.domain.member.model.RefreshToken;
import io.linkloud.api.domain.member.repository.RefreshTokenRepository;
import io.linkloud.api.global.exception.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("refreshToken Redis Test")
@SpringBootTest
class RefreshTokenServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        refreshToken = new RefreshToken(1L, "refreshToken", 10000L);
    }

    @AfterEach
    void tearDown() {
        refreshTokenRepository.deleteById(refreshToken.getMemberId());
    }

    @DisplayName("리프레시토큰 저장 등록 성공")
    @Test
    public void refreshToken_save_success() {

        // given
        CreateRefreshTokenRequestDto dto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            refreshToken.getRefreshToken(),
            refreshToken.getRefreshTokenExpiration()
        );

        refreshTokenService.createRefreshToken(dto);

        // when
        RefreshToken persistRefreshToken = refreshTokenRepository.findById(refreshToken.getMemberId())
            .orElseThrow(RuntimeException::new);

        // then
        assertThat(persistRefreshToken.getMemberId()).isEqualTo(refreshToken.getMemberId());
        assertThat(persistRefreshToken.getRefreshToken()).isEqualTo(refreshToken.getRefreshToken());
    }


    @DisplayName("리프레시토큰 갱신 성공")
    @Test
    public void refreshToken_update_success() {

        // given
        String oldRefreshTokenValue = "oldRefreshToken";
        CreateRefreshTokenRequestDto oldRefreshTokenDto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            oldRefreshTokenValue,
            refreshToken.getRefreshTokenExpiration()

        );
        refreshTokenService.createRefreshToken(oldRefreshTokenDto);

        String newRefreshTokenValue = "newRefreshToken";
        CreateRefreshTokenRequestDto newRefreshTokenDto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            newRefreshTokenValue,
            refreshToken.getRefreshTokenExpiration()

        );

        // when
        refreshTokenService.createRefreshToken(newRefreshTokenDto);

        // then
        RefreshToken isUpdatedToken = refreshTokenRepository.findById(refreshToken.getMemberId())
            .orElseThrow(RuntimeException::new);

        assertAll(
            () -> assertThat(isUpdatedToken).isNotNull(),
            () -> assertThat(isUpdatedToken.getRefreshToken()).isEqualTo(newRefreshTokenValue),
            () -> assertThat(isUpdatedToken.getRefreshToken()).isNotEqualTo(oldRefreshTokenValue)
        );

    }

    @DisplayName("리프레시 토큰 검증 통과")
    @Test
    public void refreshToken_validate_success() {
        // given
        CreateRefreshTokenRequestDto dto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            refreshToken.getRefreshToken(),
            refreshToken.getRefreshTokenExpiration()

        );

        refreshTokenService.createRefreshToken(dto);

        assertDoesNotThrow(
            () -> refreshTokenService.validateRefreshToken(refreshToken.getMemberId(),
                refreshToken.getRefreshToken()));
    }

    @DisplayName("리프레시 토큰 검증 통과 실패")
    @Test
    public void refreshToken_validate_fail() {

        // given
        String oldRefreshTokenValue = "oldRefreshToken";
        CreateRefreshTokenRequestDto oldRefreshTokenDto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            oldRefreshTokenValue,
            refreshToken.getRefreshTokenExpiration()

        );
        refreshTokenService.createRefreshToken(oldRefreshTokenDto);

        String newRefreshTokenValue = "newRefreshToken";
        CreateRefreshTokenRequestDto newRefreshTokenDto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            newRefreshTokenValue,
            refreshToken.getRefreshTokenExpiration()

        );
        refreshTokenService.createRefreshToken(newRefreshTokenDto);

        assertThatExceptionOfType(CustomException.class).isThrownBy(
            () -> refreshTokenService.validateRefreshToken(refreshToken.getMemberId(),
                oldRefreshTokenValue));
    }

    @DisplayName("리프레시 토큰 삭제 성공")
    @Test
    public void refreshToken_remove_success() {
        // given
        CreateRefreshTokenRequestDto dto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            refreshToken.getRefreshToken(),
            refreshToken.getRefreshTokenExpiration()

        );
        refreshTokenService.createRefreshToken(dto);

        // when
        refreshTokenService.removeRefreshToken(refreshToken.getMemberId());

        // then
        assertThat(refreshTokenRepository.findById(refreshToken.getMemberId())).isEmpty();
    }

}