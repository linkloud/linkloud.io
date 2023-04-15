package io.linkloud.api.global.security.auth.client.dto.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.global.security.auth.client.dto.OAuth2UserInfo;
import lombok.Getter;


/**
 * 액세스 토큰으로 받아온 사용자정보
 * JSON -> 자바 객체로변환한 클래스
 */
@Getter
public class GoogleUserInfo extends OAuth2UserInfo {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "picture")
    private String picture;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "verified_email")
    private Boolean verifiedEmail;
    private SocialType socialType = SocialType.google;



    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPicture() {
        return picture;
    }

    @Override
    public SocialType getSocialType() {
        return socialType;
    }
}
