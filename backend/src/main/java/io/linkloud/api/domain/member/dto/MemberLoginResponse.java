package io.linkloud.api.domain.member.dto;

import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import lombok.Getter;

@Getter
public class MemberLoginResponse {

    private String nickname;

    private String picture;

    private Role role;

    public MemberLoginResponse(Member member) {
        this.nickname = member.getNickname();
        this.picture = member.getPicture();
        this.role = member.getRole();
    }
}