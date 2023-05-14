package io.linkloud.api.domain.member.api;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.member.service.MemberService;
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
    public void member_me() {

    }

    @DisplayName("회원 조회 실패_없는 회원")
    @Test
    public void member_me_not_found_throws() throws Exception {

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