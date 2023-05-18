package io.linkloud.api.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import io.linkloud.api.domain.member.model.RefreshToken;
import io.linkloud.api.domain.member.repository.RefreshTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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


}