package io.linkloud.api.global.security.auth.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import io.linkloud.api.global.security.auth.client.dto.GoogleAccessToken;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuthClientImpl implements OAuthClient {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${google.userinfo.request.url}")
    private String GOOGLE_USERINFO_REQUEST_URL;

    @Value("${google.token.request.url}")
    private String GOOGLE_TOKEN_REQUEST_URL;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;


    @Override
    public String getAccessToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", REDIRECT_URI);
        String responseBody = "";
        
        try {
            responseBody = webClient.post()
                .uri(GOOGLE_TOKEN_REQUEST_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional()
                .orElseThrow(() -> new IOException("구글 AccessToken 요청에 실패했습니다"));
        } catch (IOException e) {
            log.error("GoogleAccessToken 요청 중 예외 발생: " + e.getMessage());
            throw new LogicException(ExceptionCode.JSON_REQUEST_FAILED);
        }
        log.info("GoogleAccessToken 요청 성공");
        return convertGoogleAuthToken(responseBody).getAccessToken();
    }

    /**
     *  code,clientId,clientSecret 로 요청받은 구글 액세스토큰을 JSON -> 자바 객체로 변환
     */
    private GoogleAccessToken convertGoogleAuthToken(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, GoogleAccessToken.class);
        } catch (JsonProcessingException e) {
            // TODO : 커스텀 예외 처리
            throw new LogicException(ExceptionCode.JSON_REQUEST_FAILED);
        }
    }
}
