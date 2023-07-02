package io.linkloud.api.domain.member.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponseDto {

    private String accessToken;


    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
