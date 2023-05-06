package io.linkloud.api.domain.member.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.auth.jwt.dto.SecurityMember;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void member_me() throws Exception {
        // given
        Member member = Member.builder()
            .id(1L)
            .role(Role.USER)
            .email("email@test.com")
            .nickname("memberNickname")
            .picture("memberPicture")
            .socialType(SocialType.google)
            .build();
        Member savedMember = memberRepository.save(member);

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
            .andDo(document("member/me"));

    }

}

