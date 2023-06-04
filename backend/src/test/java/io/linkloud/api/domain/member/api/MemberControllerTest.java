package io.linkloud.api.domain.member.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @Autowired
    Gson gson;
    Member member;
    MemberNicknameRequestDto memberNicknameRequest;
    String accessToken;
    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .id(1L)
                .nickname("member1_google")
                .email("member1@email.com")
                .socialType(SocialType.google)
                .role(Role.MEMBER)
                .picture("picture1")
                .socialId("socialId1")
                .build();
        memberRepository.save(member);
        accessToken = jwtProvider.generateAccessToken(member.getId(), member.getSocialType());
        memberNicknameRequest = new MemberNicknameRequestDto("new_nickname");
    }

    @AfterEach
    public void cleanUp() {
        memberRepository.findById(member.getId());
    }



    String BASE_URL = "/api/v1/member";


    @DisplayName("회원정보 - 가입한지3일이 안지난 회원 NEW_MEMBER")
    @Test
    public void fineMe_success_new_member() throws Exception {

        Member mockMember = mock(Member.class);
        when(mockMember.getNickname()).thenReturn("KimMinJae");
        when(mockMember.getPicture()).thenReturn("picture");
        when(mockMember.getRole()).thenReturn(Role.NEW_MEMBER);

        MemberLoginResponse mockResponse = new MemberLoginResponse(mockMember);
        when(memberService.fetchPrincipal(anyLong())).thenReturn(mockResponse);

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value(mockMember.getNickname()))
                .andExpect(jsonPath("$.data.picture").value(mockMember.getPicture()))
                .andExpect(jsonPath("$.data.role").value(mockMember.getRole().name()))
                .andDo(document("member/me/success/newMember",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.nickname").description("회원의 닉네임."),
                                fieldWithPath("data.picture").description("회원의 프로필 사진 URI."),
                                fieldWithPath("data.role").description("가입한지 3일 이내 회원의 권한")
                        )));
    }

    @DisplayName("회원정보 - 가입한지 3일지난 회원 MEMBER")
    @Test
    public void fineMe_success_member() throws Exception {

        Member mockMember = mock(Member.class);
        when(mockMember.getNickname()).thenReturn("Son");
        when(mockMember.getPicture()).thenReturn("picture");
        when(mockMember.getRole()).thenReturn(Role.MEMBER);

        MemberLoginResponse mockResponse = new MemberLoginResponse(mockMember);
        when(memberService.fetchPrincipal(anyLong())).thenReturn(mockResponse);

        ResultActions actions = mockMvc.perform(
                get(BASE_URL + "/me")
                        .header("Authorization", "Bearer " + accessToken));

        actions.andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/me/success/member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data.nickname").description("회원의 닉네임."),
                                fieldWithPath("data.picture").description("회원의 프로필 사진 URI."),
                                fieldWithPath("data.role").description("가입한지 3일 지난 회원의 권한")
                        )));
    }

    @DisplayName("회원 조회 실패_만료된 액세스토큰")
    @Test
    public void member_me_expired_accessToken() throws Exception {
        // given
        doThrow(new CustomException(AuthExceptionCode.EXPIRED_ACCESS_TOKEN))
            .when(memberService).fetchPrincipal(any(Long.class));

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andDo(document("member/me/fail/accessToken",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("status").description("에러 코드"),
                    fieldWithPath("message").description("에러 메시지"),
                    fieldWithPath("fieldErrors").description("필드 에러"),
                    fieldWithPath("violationErrors").description("검증 에러")
                )
            ));
    }

    @DisplayName("회원 조회 실패_없는 회원")
    @Test
    public void member_me_not_found_throws() throws Exception {
        // given
        doThrow(new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND))
            .when(memberService).fetchPrincipal(any(Long.class));

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(LogicExceptionCode.MEMBER_NOT_FOUND.getStatus()))
            .andExpect(jsonPath("$.message").value(LogicExceptionCode.MEMBER_NOT_FOUND.getMessage()))
            .andDo(document("member/me/fail/notfound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("status").description("에러 코드"),
                    fieldWithPath("message").description("에러 메시지"),
                    fieldWithPath("fieldErrors").description("필드 에러"),
                    fieldWithPath("violationErrors").description("검증 에러")
                )
            ));
    }

    @DisplayName("회원 닉네임 변경 성공")
    @Test
    public void updateNickname_success() throws Exception {

        String content = gson.toJson(memberNicknameRequest);


        ResultActions actions = mockMvc.perform(patch(BASE_URL + "/nickname")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(content));

        actions
                .andExpect(status().isNoContent())
                .andDo(document("member/nickname/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("nickname").description("수정할 닉네임")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("액세스 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        )
                ));
    }


    @DisplayName("회원 닉네임 변경 실패 중복 닉네임 예외처리")
    @Test
    public void updateNickname_duplicated_nickname_throws() throws Exception {
        // given
        String content = gson.toJson(memberNicknameRequest);

        doThrow(new CustomException(LogicExceptionCode.MEMBER_ALREADY_EXISTS))
            .when(memberService).updateNickname(anyLong(), any(MemberNicknameRequestDto.class));


        ResultActions actions = mockMvc.perform(patch(BASE_URL + "/nickname")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(content));

        actions
                .andDo(print())
                .andExpect(status().isConflict())
                .andDo(document("member/nickname/fail/duplicated",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("nickname").description("수정할 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description(
                                        "HTTP 상태 코드. 중복된 닉네임일 경우 409 (Conflict)을 반환."),
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("fieldErrors").description("필드 에러 리스트"),
                                fieldWithPath("violationErrors").description("벨리데이션 에러 리스트")
                        )));
    }
}