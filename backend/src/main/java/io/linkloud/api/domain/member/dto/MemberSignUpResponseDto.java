package io.linkloud.api.domain.member.dto;

import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import lombok.Getter;

@Getter
public class MemberSignUpResponseDto {
    private final Long id;
    private final String nickname;
    private final Role role;

    private final SocialType socialType;

    public MemberSignUpResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.role = member.getRole();
        this.socialType = member.getSocialType();
    }

}
