package io.linkloud.api.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.linkloud.api.domain.member.dto.CreateRefreshTokenRequestDto;
import io.linkloud.api.domain.member.model.RefreshToken;
import io.linkloud.api.domain.member.repository.RefreshTokenRepository;
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
        refreshToken = new RefreshToken(1L, "refreshToken");
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
            refreshToken.getRefreshToken()
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
            oldRefreshTokenValue
        );
        refreshTokenService.createRefreshToken(oldRefreshTokenDto);

        String newRefreshTokenValue = "newRefreshToken";
        CreateRefreshTokenRequestDto newRefreshTokenDto = new CreateRefreshTokenRequestDto(
            refreshToken.getMemberId(),
            newRefreshTokenValue
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
}