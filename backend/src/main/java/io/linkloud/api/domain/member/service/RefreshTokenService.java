package io.linkloud.api.domain.member.service;

import io.linkloud.api.domain.member.dto.CreateRefreshTokenRequestDto;
import io.linkloud.api.domain.member.model.RefreshToken;
import io.linkloud.api.domain.member.repository.RefreshTokenRepository;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
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
     * @param dto 요청회원_ID,요청회원_refreshToken
     */
    public void createRefreshToken(CreateRefreshTokenRequestDto dto) {
        RefreshToken refreshToken = new RefreshToken(
            dto.getMemberId(),
            dto.getRefreshToken(),
            dto.getRefreshTokenExpiration());
        refreshTokenRepository.save(refreshToken);
        log.info("refreshToken 토큰 저장완료");
    }

    /**
     * 회원이 요청한 refreshToken 과 서버의 refreshToken 이 유효한지 검증하는 로직
     * @param memberId 회원 ID
     * @param refreshToken 회원 리프레시 토큰
     */
    public void validateRefreshToken(Long memberId, String refreshToken) {
        RefreshToken foundRefreshToken = findRefreshTokenByMemberId(memberId);
        foundRefreshToken.validateRefreshToken(refreshToken);
    }

    /** refresh token 제거 */
    public void removeRefreshToken(Long memberId) {
        RefreshToken foundRefreshToken = findRefreshTokenByMemberId(memberId);
        refreshTokenRepository.deleteById(foundRefreshToken.getMemberId());
        log.info("리프레시 토큰 삭제");
    }

    /**
     * 회원 ID 로 리프레시 토큰 찾기
     * @param memberId 회원 ID
     * @return 리프레시 토큰 객체
     */
    private RefreshToken findRefreshTokenByMemberId(Long memberId) {
        return refreshTokenRepository.findById(memberId)
            .orElseThrow(() -> {
                log.error("Member[{}]의 리프레시토큰이 DB에 존재하지 않습니다.", memberId);
                return new CustomException(AuthExceptionCode.EXPIRED_REFRESH_TOKEN);
            });
    }

}
