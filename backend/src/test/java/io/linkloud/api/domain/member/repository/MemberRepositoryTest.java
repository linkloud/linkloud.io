package io.linkloud.api.domain.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    final String socialId = "1234";
    final SocialType google = SocialType.google;

    final String nickname = "SON";

    @BeforeEach
    public void beforeEach() {

        // given
        Member member = Member.builder()
            .id(1L)
            .socialId(socialId)
            .nickname(nickname)
            .role(Role.MEMBER)
            .email("email")
            .socialType(google)
            .build();
        memberRepository.save(member);
    }

    @Test
    void findMemberBySocialTypeAndSocialId() {

        // when
        Member findMember = memberRepository.findMemberBySocialTypeAndSocialId(google, socialId)
            .orElseThrow(() -> new IllegalArgumentException(
                "해당 회원 없다"));
        Long id = findMember.getId();

        // then
        Assertions.assertThat(id).isEqualTo(1L);
        Assertions.assertThat(socialId).isEqualTo(findMember.getSocialId());
        Assertions.assertThat(nickname).isEqualTo(findMember.getNickname());
        Assertions.assertThat(nickname).isNotEqualTo("SIUUUUU");


    }

    @Test
    void findMemberByIdAndEmail() {
        // given
        Long memberId = 1L;
        String email = "email";

        // when
        Member member = memberRepository.findMemberByIdAndEmail(memberId, email)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원 없다"));

        // then
        Assertions.assertThat(memberId).isEqualTo(member.getId());
        Assertions.assertThat(email).isEqualTo(member.getEmail());

    }
}