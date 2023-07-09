package io.linkloud.api.domain.member.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.domain.member.dto.RefreshAccessTokenRequest;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.security.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AuthService authService;

    final String BASE_URL = "/api/v1/auth";

    AuthRequestDto authRequest = new AuthRequestDto("google", "code1234");
    AuthResponseDto authResponse = new AuthResponseDto("access_token");
    RefreshAccessTokenRequest refreshTokenRequest = new RefreshAccessTokenRequest("refreshToken_value");
    AuthResponseDto newTokenAuthResponse = new AuthResponseDto("new_access_token");


    @DisplayName("소셜 로그인 API 호출 성공")
    @Test
    void authenticate_success() throws Exception {
        // given
        String content = gson.toJson(authRequest);
        String SocialType = "google";
        given(authService.authenticate(any(AuthRequestDto.class),any())).willReturn(authResponse);

        // when
        mockMvc.perform(post(BASE_URL + "/{socialType}", SocialType)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("auth/authenticate_success",
                pathParameters(
                    parameterWithName("socialType").description("소셜 타입 (google 등)")
                ),
                requestFields(
                    fieldWithPath("socialType").description("소셜 타입 (google 등)"),
                    fieldWithPath("code").description("일회용 oauth 액세스 토큰 요청 인가 코드")
                ),
                    responseFields(
                            fieldWithPath("data.accessToken").description("Access Token")
                    )
            ));
        verify(authService).authenticate(any(),any());
    }

    @Test
    @DisplayName("authenticate 실패 - OAuthClient 구현체를 찾을 수 없을 때")
    void authenticate_fail_invalid_social_type() throws Exception {
        // given
        String socialType = "INVALID_SOCIAL_TYPE";
        String content = gson.toJson(authRequest);

        // when
        when(authService.authenticate(any(),any())).thenThrow(
            new CustomException(AuthExceptionCode.INVALID_SOCIAL_TYPE));

        mockMvc.perform(post(BASE_URL + "/{socialType}", socialType)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isNotFound())
            .andDo(document("auth/authenticate_fail/implementation",
                pathParameters(
                    parameterWithName("socialType").description("소셜 타입 (google 등)")
                ),
                requestFields(
                    fieldWithPath("socialType").description("소셜 로그인 타입"),
                    fieldWithPath("code").description("소셜 로그인 후 발급받은 코드")
                ),
                responseFields(
                    fieldWithPath("status").description("HTTP status 상태 코드"),
                    fieldWithPath("message").description("에러 메시지"),
                    fieldWithPath("fieldErrors").ignored(),
                    fieldWithPath("violationErrors").ignored()
                )));
    }

    @Test
    @DisplayName("authenticate 실패 - OAuth 서버 액세스 토큰 요청 에러")
    void authenticate_fail_oauth_accessToken_request() throws Exception {
        // given
        String socialType = "REQUEST_FAIL_OAUTH_ACCESS_TOKEN";
        String content = gson.toJson(authRequest);



        // when
        when(authService.authenticate(any(),any())).thenThrow(
            new CustomException(LogicExceptionCode.JSON_REQUEST_FAILED));

        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{socialType}", socialType)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isUnauthorized());

        actions.andDo(document("auth/authenticate_fail/accesstoken",
            pathParameters(
                parameterWithName("socialType").description("소셜 타입 (google 등)")
            ),
            responseFields(
                fieldWithPath("status").description("HTTP status 상태 코드"),
                fieldWithPath("message").description("에러 메시지"),
                fieldWithPath("fieldErrors").ignored(),
                fieldWithPath("violationErrors").ignored()
            )));
    }

    @Test
    @DisplayName("authenticate 실패 - OAuth 서버 사용자 정보 요청 에러")
    void authenticate_fail_userinfo_request() throws Exception {
        // given
        String socialType = "REQUEST_FAIL_USERINFO";
        String content = gson.toJson(authRequest);



        // when
        when(authService.authenticate(any(),any())).thenThrow(
            new CustomException(LogicExceptionCode.JSON_REQUEST_FAILED));

        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{socialType}", socialType)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isUnauthorized());

        actions.andDo(document("auth/authenticate_fail/userinfo",
            pathParameters(
                parameterWithName("socialType").description("소셜 타입 (google 등)")
            ),
            responseFields(
                fieldWithPath("status").description("HTTP status 상태 코드"),
                fieldWithPath("message").description("에러 메시지"),
                fieldWithPath("fieldErrors").ignored(),
                fieldWithPath("violationErrors").ignored()
            )));
    }

    @Test
    @DisplayName("refreshToken 요청 - 성공")
    public void refreshToken() throws Exception {
        // given
        String content = gson.toJson(refreshTokenRequest);
        given(authService.refreshTokenAndAccessToken(any(),any())).willReturn(newTokenAuthResponse);

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/refresh")
                        .cookie(new Cookie("refreshToken", "aaaaaa.bbbbbb.ccccc"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        actions.andDo(print())
                .andDo(document("auth/refreshToken_success",
                                requestFields(
                                        fieldWithPath("refreshToken").description("리프레시 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("data.accessToken").description("새로 발급된 액세스 토큰")
                                )
                        )
                );
    }

    @Test
    @DisplayName("refreshToken 요청 실패 - refreshToken 이 유효하지 않음  ")
    public void refreshToken_fail_invalid_refreshToken() throws Exception {

        // given
        RefreshAccessTokenRequest invalidRequestToken = new RefreshAccessTokenRequest("INVALID_REFRESH_TOKEN");

        String content = gson.toJson(invalidRequestToken);

        when(authService.refreshTokenAndAccessToken(any(),any())).thenThrow(
            new CustomException(AuthExceptionCode.INVALID_TOKEN));

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/refresh")
                    .cookie(new Cookie("refreshToken", "aaaaaa.bbbbbb.ccccc"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isUnauthorized());


        actions.andDo(print())
            .andDo(document("auth/refreshToken_fail/invalid",
                    requestFields(
                        fieldWithPath("refreshToken").description("유효하지 않은 리프레시 토큰(유효X,변조,null or empty)")
                    ),
                    responseFields(
                        fieldWithPath("status").description("HTTP status 상태 코드"),
                        fieldWithPath("message").description("에러 메시지"),
                        fieldWithPath("fieldErrors").ignored(),
                        fieldWithPath("violationErrors").ignored()
                    )
                )
            );
    }

    @Test
    @DisplayName("refreshToken 요청 실패 - refreshToken 이 만료됨  ")
    public void refreshToken_fail_expired_token() throws Exception {

        // given
        RefreshAccessTokenRequest invalidRequestToken = new RefreshAccessTokenRequest("EXPIRED_TOKEN");

        String content = gson.toJson(invalidRequestToken);

        when(authService.refreshTokenAndAccessToken(any(),any())).thenThrow(
            new CustomException(AuthExceptionCode.EXPIRED_REFRESH_TOKEN));

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/refresh")
                .cookie(new Cookie("refreshToken", "aaaaaa.bbbbbb.ccccc"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isUnauthorized());


        actions.andDo(print())
            .andDo(document("auth/refreshToken_fail/expired",
                    requestFields(
                        fieldWithPath("refreshToken").description("만료된 리프레시 토큰")
                    ),
                    responseFields(
                        fieldWithPath("status").description("HTTP status 상태 코드"),
                        fieldWithPath("message").description("에러 메시지"),
                        fieldWithPath("fieldErrors").ignored(),
                        fieldWithPath("violationErrors").ignored()
                    )
                )
            );
    }
    @Test
    @DisplayName("로그아웃")
    public void logout() throws Exception {
        Cookie tempCookie = new Cookie("refreshToken", "aaaaaa.bbbbbb.ccccc");
        tempCookie.setMaxAge(10000);

        ResultActions actions = mockMvc.perform(post(BASE_URL + "/logout")
                .cookie(tempCookie)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(cookie().maxAge(tempCookie.getName(),0));

        actions
            .andDo(document("auth/logout")
            );

    }



}

