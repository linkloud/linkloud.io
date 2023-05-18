package io.linkloud.api.domain.member.service;

import io.linkloud.api.domain.member.dto.CreateRefreshTokenRequestDto;
import io.linkloud.api.domain.member.model.RefreshToken;
import io.linkloud.api.domain.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * refreshToken 등록
     * @param dto
     */
    public void createRefreshToken(CreateRefreshTokenRequestDto dto) {
        RefreshToken refreshToken = new RefreshToken(dto.getMemberId(), dto.getRefreshToken());
        refreshTokenRepository.save(refreshToken);
        log.info("refreshToken 토큰 저장완료");
    }

}
