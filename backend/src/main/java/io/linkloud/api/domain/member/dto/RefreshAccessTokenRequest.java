package io.linkloud.api.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RefreshAccessTokenRequest {

    private String refreshToken;
}