package io.linkloud.api.global.security.auth.client.dto.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class GoogleAccessToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

}