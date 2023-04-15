package io.linkloud.api.global.security.auth.client;


import io.linkloud.api.global.security.auth.client.dto.OAuthAttributes;

public interface OAuthClient {

    /** 1. 인증 코드 이용해 access Token 얻어온다 */
    String getAccessToken(String code);

    /** 2. 액세스 토큰으로 사용자 정보를 가져온다*/
    OAuthAttributes getUserInfo(String accessToken);
}