package io.linkloud.api.domain.member.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import io.linkloud.api.global.security.auth.jwt.dto.SecurityMember;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.save(Member.builder()
            .id(1L)
            .nickname("member1_google")
            .email("member1@email.com")
            .socialType(SocialType.google)
            .role(Role.USER)
            .picture("picture1")
            .socialId("socialId1")
            .refreshToken("refreshToken1")
            .build());

        memberRepository.save(Member.builder()
            .id(2L)
            .nickname("member2_google")
            .email("member2@email.com")
            .socialType(SocialType.google)
            .role(Role.USER)
            .picture("picture2")
            .socialId("socialId2")
            .refreshToken("refreshToken2")
            .build());
    }

    @DisplayName("회원 조회 성공")
    @Test
    public void member_me() throws Exception {

        // given
        Member savedMember = memberRepository.findById(1L)
            .orElseThrow(() -> new LogicException(ExceptionCode.MEMBER_NOT_FOUND));

        SecurityMember securityMember = new SecurityMember(
            savedMember.getId(),
            savedMember.getNickname(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            savedMember.getPicture(),
            savedMember.getSocialType()
        );

        MemberLoginResponse memberLoginResponse = new MemberLoginResponse(savedMember);
        given(memberService.fetchPrincipal(securityMember)).willReturn(memberLoginResponse);

        mockMvc.perform(get("/api/v1/member/me").with(user(securityMember)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.nickname").value(savedMember.getNickname()))
            .andExpect(jsonPath("$.data.picture").value(savedMember.getPicture()))
            .andExpect(jsonPath("$.data.role").value(Role.USER.toString()))
            .andDo(print())
            .andDo(document("member/me/success",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("data.nickname").description("회원의 닉네임."),
                        fieldWithPath("data.picture").description("회원의 프로필 사진 URI."),
                        fieldWithPath("data.role").description("회원의 권한")
                    )
                )
            );


    }

    @DisplayName("회원 조회 실패_없는 회원")
    @Test
    public void member_me_not_found_throws() throws Exception {

        // given
        doThrow(new LogicException(ExceptionCode.MEMBER_NOT_FOUND))
            .when(memberService).fetchPrincipal(any(SecurityMember.class));
        Member member = memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("회원을 찾지 못했습니다"));

        SecurityMember securityMember = new SecurityMember(
            10000L,
            member.getNickname(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            member.getPicture(),
            member.getSocialType()
        );

        mockMvc.perform(get("/api/v1/member/me").with(user(securityMember)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(ExceptionCode.MEMBER_NOT_FOUND.getStatus()))
            .andExpect(jsonPath("$.message").value(ExceptionCode.MEMBER_NOT_FOUND.getMessage()))
            .andDo(print())
            .andDo(document("member/me/fail",
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

    }

    @DisplayName("회원 닉네임 변경 실패 중복 닉네임 예외처리")
    @Test
    public void updateNickname_duplicated_nickname_throws() throws Exception {

    }
}