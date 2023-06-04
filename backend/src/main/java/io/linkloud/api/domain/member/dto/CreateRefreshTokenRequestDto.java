package io.linkloud.api.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateRefreshTokenRequestDto {
    private final Long memberId;
    private final String refreshToken;
    private final long refreshTokenExpiration;
}

