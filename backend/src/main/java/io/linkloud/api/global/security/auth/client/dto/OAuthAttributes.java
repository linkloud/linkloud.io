package io.linkloud.api.global.security.auth.client.dto;

import io.linkloud.api.domain.member.model.SocialType;
import lombok.Getter;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로,
 * 소셜별로 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {

    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)


    // 1231321321424141421 (google 로 따지면 sub 의 값을 가져옴 기본키)
    private String socialId;

    // test@gmail.com
    private String email;

    //  프로필사진
    private String picture;

    private SocialType socialType;



    public OAuthAttributes(OAuth2UserInfo oauth2UserInfo) {
        this.email = oauth2UserInfo.getEmail();
        this.picture = oauth2UserInfo.getPicture();
        this.socialId = oauth2UserInfo.getId();
        this.socialType = oauth2UserInfo.getSocialType();
    }


}
