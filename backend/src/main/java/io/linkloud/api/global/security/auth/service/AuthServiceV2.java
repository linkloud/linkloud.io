package io.linkloud.api.global.security.auth.service;

import io.linkloud.api.domain.member.dto.AuthV2Dto.AccessTokenRequest;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AccessTokenResponse;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AuthRequest;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AuthResponse;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceV2 {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse authenticate(AuthRequest authRequest) {
        Long memberId = loginOrSignup(authRequest);
        return new AuthResponse(memberId);
    }


    //  provider(google), providerId, DB 조회
    //  있으면 memberID 리턴(가입된 유저) / 없으면 가입 후 memberID 리턴(가입 안 된 유저)
    private Long loginOrSignup(AuthRequest authRequest) {

        String email = authRequest.getEmail();
        SocialType socialType = authRequest.getSocialType();
        String nickname = extractNicknameFromRequest(email, socialType);
        String socialId = authRequest.getSocialId();
        String picture = authRequest.getPicture();


        Member member = memberRepository.findMemberBySocialTypeAndSocialId(socialType, socialId)
            .orElseGet(() -> {
                log.info("가입되지 않은 회원입니다.");

                Member newMember = Member.builder()
                        .email(email)
                        .role(Role.MEMBER)
                        .nickname(nickname)
                        .socialType(socialType)
                        .picture(picture)
                        .socialId(socialId)
                        .build();
                    log.info("회원가입 완료 (Nickname) ={}", newMember.getNickname());
                    return memberRepository.save(newMember);
                }
            );
        log.info("회원 PK ID : {}",member.getId());
        return member.getId();
    }

    // 이메일에서 닉네임 추출
    // hello_gmail
    private String extractNicknameFromRequest(String email, SocialType socialType) {
        String[] splitEmail = email.split("@");
        return splitEmail[0] + "_" + socialType.name();
    }

    @Transactional(readOnly = true)
    public AccessTokenResponse createAccessToken(AccessTokenRequest tokenRequest) {

        String email = tokenRequest.getEmail();
        Long memberId = tokenRequest.getMemberId();

        // 회원정보가 없다면 403
        memberRepository.findMemberByIdAndEmail(memberId,email).orElseThrow(
            () -> new CustomException(LogicExceptionCode.MEMBER_NOT_AUTHORIZED)
        );

        // 회원 정보가 있다면 회원정보로 accessToken 생성 후, accessToken 리턴
        String accessToken = jwtProvider.generateAccessTokenV2(memberId,email);
        log.info("createdAccessToken V2 : {}",accessToken);
        return new AccessTokenResponse(accessToken);
    }

}
