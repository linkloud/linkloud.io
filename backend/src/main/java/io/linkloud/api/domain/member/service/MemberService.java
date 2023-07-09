package io.linkloud.api.domain.member.service;

import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.member.dto.MemberSignUpResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.security.auth.client.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // #1 멤버 회원가입
    @Transactional
    public MemberSignUpResponseDto signUpIfNotExists(OAuthAttributes userInfo) {
        Member member = createMember(userInfo);
        return new MemberSignUpResponseDto(member);
    }

    /**
     * # 1
     * 1. 닉네임 설정을 위한 사용자 이메일에서 아이디 부분 추출
     * 2. 이미 존재하는 유저라면 Member 객체 바로 리턴
     * 3. 존재하지 않는 유저라면 유저 DB 저장에 저장 후 Member 객체 리턴
     * @param userInfo : OAuth 사용자 정보 Class
     * @return Member
     */
    private Member createMember(OAuthAttributes userInfo) {
        String extractedName = extractNameFromEmail(userInfo);
        return memberRepository.findByEmailAndSocialId(userInfo.getEmail(), userInfo.getSocialId())
            .orElseGet(() -> {
                Member newMember = Member.builder()
                    .email(userInfo.getEmail())
                    .role(Role.MEMBER) // NEW_MEMBER 일경우, 가입 후 3일 글쓰기 제한가능
                    .nickname(extractedName)
                    .socialType(userInfo.getSocialType())
                    .picture(userInfo.getPicture())
                    .socialId(userInfo.getSocialId())
                    .build();
                log.info("newMember={}",newMember);
                return memberRepository.save(newMember);
            });
    }

    /**
     * 회원 이메일을 "@"로 기준으로 나눈 후, 아이디 부분만 추출
     * @param userInfo (memberID@gmail.com)
     * @return memberID
     */
    private String extractNameFromEmail(OAuthAttributes userInfo) {
        String email = userInfo.getEmail();
        String[] splitEmail = email.split("@");
        return splitEmail[0] + "_" + userInfo.getSocialType().name();
    }

    public MemberLoginResponse fetchPrincipal(Long memberId) {
        Member member = fetchMemberById(memberId);
        return new MemberLoginResponse(member);
    }

    /**
     * Member 객체 리턴(주의 Member 엔티티 자체를 리턴함)
     * @param id memberId
     * @return Member
     */
    public Member fetchMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND));
    }


    /**
     *
     * @param memberId 회원 ID
     * @param nicknameRequestDto 닉네임 Dto
     */
    @Transactional
    public void updateNickname(Long memberId, MemberNicknameRequestDto nicknameRequestDto) {
        String requestNickname = nicknameRequestDto.getNickname();
        Member member = fetchMemberById(memberId);
        if (!member.getNickname().equals(requestNickname)) {
            isNicknameDuplicated(requestNickname);
            member.updateNickname(requestNickname);
        }
    }

    /**
     * 회원 닉네임 중복검사
     * @param nickname 회원 닉네임
     */
    private void isNicknameDuplicated(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new CustomException(LogicExceptionCode.MEMBER_ALREADY_EXISTS);
        }
    }
}
