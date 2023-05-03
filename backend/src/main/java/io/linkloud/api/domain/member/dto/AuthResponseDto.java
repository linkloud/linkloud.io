package io.linkloud.api.domain.member.dto;

import io.linkloud.api.domain.member.model.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponseDto {

    private Long id;

    public AuthResponseDto(MemberSignUpResponseDto memberSignUpResponseDto) {
        this.id = memberSignUpResponseDto.getId();
    }


}
