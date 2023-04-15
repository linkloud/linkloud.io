package io.linkloud.api.global.security.auth.client.dto;

import io.linkloud.api.domain.member.model.SocialType;

/**
 * GOOGLE,GITHUB 사용자 정보를 JSON 으로 받아와
 * 자바 객체 클래스(GoogleUserInfo.class) 로 변환했을 때
 * 이 클래스를 상속함으로써
 * OAuthAttributes 클래스 생성자에 이 클래스를 파라매터로 넣음으로써
 * 각 클래스별로 따로 만들지않고 이 클래스로 묶어서 사용가능
 */
public abstract class OAuth2UserInfo {

    public abstract String getId();
    public abstract String getEmail();
    public abstract String getPicture();

    public abstract SocialType getSocialType();

}