package io.linkloud.api.domain.member.dto;

import io.linkloud.api.domain.member.model.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthV2Dto {

    // 로그인 또는 회원가입 Request
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthRequest {

        private String socialId;

        private String email;

        private SocialType socialType;

        private String picture;

    }


    // 로그인 또는 회원가입 Response
    public record AuthResponse(Long memberId) {}

    // accessToken 생성할 때 필요한 회원 정보 Request
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccessTokenRequest{

        private SocialType socialType;

        private String email;

        private Long memberId;
    }

    // AccessToken Response
    public record AccessTokenResponse(String accessToken) {}

}
