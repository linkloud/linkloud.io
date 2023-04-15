package io.linkloud.api.global.security.auth.client;

/**
 * OAuth resource 서버에 access token, userinfo 요청 인터페이스
 */
public interface OAuthClient {

    /** 1. 인증 코드 이용해 access Token 얻어온다 */
    String getAccessToken(String code);

}
