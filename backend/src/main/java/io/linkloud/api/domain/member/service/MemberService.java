package io.linkloud.api.domain.member.service;

import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import io.linkloud.api.global.security.auth.client.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member registerIfNotExists(OAuthAttributes userInfo) {
        validateDuplicatedEmail(userInfo.getEmail());
        // 이미 존재하는 유저라면 member 바로 리턴
        Member member = memberRepository.findByEmailAndSocialId(userInfo.getEmail(),
                userInfo.getSocialId())
            .orElseGet(() -> { // 만약 해당 회원 정보가 존재하지 않으면 아래 로직 실행
                {
                    Member newMember = Member.builder()
                        .email(userInfo.getEmail())
                        .role(Role.GUEST) // 첫 회원가입시 GUEST (닉네임 설정하기위해)
                        .socialType(userInfo.getSocialType())
                        .picture(userInfo.getPicture())
                        .socialId(userInfo.getSocialId())
                        .build();
                    log.info("newMember={}", newMember);
                    return memberRepository.save(newMember);
                }
            });
        return member;
    }

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            // TODO : 예외처리
            throw new LogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
        }
    }
}
