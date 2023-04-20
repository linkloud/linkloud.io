package io.linkloud.api.domain.member.dto;

import io.linkloud.api.domain.member.model.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponseDto {

    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;




}
