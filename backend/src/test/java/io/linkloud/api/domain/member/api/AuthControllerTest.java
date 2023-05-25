package io.linkloud.api.domain.member.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.security.auth.service.AuthService;
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

    AuthRequestDto authRequest = new AuthRequestDto("google", "code1234");
    AuthResponseDto authResponse = new AuthResponseDto("access_token","refresh_token");

    @DisplayName("소셜 로그인 API 호출 성공")
    @Test
    void authenticate_success() throws Exception {
        // given

        String content = gson.toJson(authRequest);
        given(authService.authenticate(any(AuthRequestDto.class))).willReturn(authResponse);

        // when
        mockMvc.perform(post("/api/v1/auth/{socialType}", "google")
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
                    fieldWithPath("data.accessToken").description("Access Token"),
                    fieldWithPath("data.refreshToken").description("Refresh Token")
                )
            ));


        verify(authService).authenticate(any());
    }

    @Test
    @DisplayName("authenticate 실패 - OAuthClient 구현체를 찾을 수 없을 때")
    void authenticate_fail_invalid_social_type() throws Exception {
        // given
        String socialType = "INVALID_SOCIAL_TYPE";
        String content = gson.toJson(authRequest);


        when(authService.authenticate(any())).thenThrow(new CustomException(AuthExceptionCode.INVALID_SOCIAL_TYPE));

        // when
        mockMvc.perform(post("/api/v1/auth/{socialType}", socialType)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isNotFound())
                .andDo(document("auth/authenticate_failure",
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

}