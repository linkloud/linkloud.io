package io.linkloud.api.domain.member.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    NEW_MEMBER("NEW_MEMBER"), // 가입한지 3일이 안지난 유저
    MEMBER("MEMBER"),         // 가입한지 3일이 지난 유저
    ADMIN("ADMIN");      // 관리자

    private final String key;

}
