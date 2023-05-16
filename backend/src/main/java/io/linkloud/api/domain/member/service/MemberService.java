package io.linkloud.api.domain.member.service;

import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.member.dto.MemberSignUpResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.ExceptionCode.ExceptionCode;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.security.auth.client.dto.OAuthAttributes;
import io.linkloud.api.global.security.auth.jwt.dto.SecurityMember;
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
                    .role(Role.USER)
                    .nickname(extractedName) // nickname 은 Not Null 이므로 임시 닉네임 설정
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

    public MemberLoginResponse fetchPrincipal(SecurityMember principalMember) {
        Member member = fetchMemberById(principalMember.getId());
        return new MemberLoginResponse(member);
    }

    public Member fetchMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND));
    }

    /**
     * 회원 닉네임 변경
     * 현재 닉네임과 변경하려는 닉네임이 같다면 실행 X
     * 중복된 닉네임이 있다면 예외처리
     * @param principalMember 인증요청한 회원
     * @param nicknameRequestDto 회원 요청 닉네임
     */
    @Transactional
    public void updateNickname(SecurityMember principalMember, MemberNicknameRequestDto nicknameRequestDto) {
        String requestNickname = nicknameRequestDto.getNickname();
        Member member = fetchMemberById(principalMember.getId());
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
