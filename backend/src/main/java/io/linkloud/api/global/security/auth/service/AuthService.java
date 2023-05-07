package io.linkloud.api.global.security.auth.service;

import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.domain.member.dto.MemberSignUpResponseDto;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import io.linkloud.api.global.security.auth.client.OAuthClient;
import io.linkloud.api.global.security.auth.client.dto.OAuthAttributes;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final Map<String, OAuthClient> oAuthClients;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;


    /**
     * 사용자 인증하고 JWT 토큰 발급
     * 1. 인증 코드(code)를 이용해 access token 받기
     * 2. access token 을 이용해 사용자 정보 받기
     * 3. DB에 사용자 정보 없으면 저장, 있으면 사용자 바로 리턴
     * 4. JWT 토큰 발급 후 반환
     */
    public AuthResponseDto authenticate(AuthRequestDto dto) {
        OAuthClient oAuthClient = oAuthClients.get(dto.getSocialType() + "OAuthClientImpl");
        if (oAuthClient == null) {
            throw new LogicException(ExceptionCode.INVALID_SOCIAL_TYPE);
        }
        // 1
        String oAuthClientAccessToken = oAuthClient.getAccessToken(dto.getCode());

        // 2
        OAuthAttributes oAuthClientUserInfo = oAuthClient.getUserInfo(oAuthClientAccessToken);

        // 3
        MemberSignUpResponseDto memberDto = memberService.signUpIfNotExists(oAuthClientUserInfo);
        Long memberId = memberDto.getId();

        String jwtAccessToken = jwtProvider.generateAccessToken(memberId,memberDto.getSocialType());
        String jwtRefreshToken = jwtProvider.generateRefreshToken();

        // 4
        return new AuthResponseDto(jwtAccessToken,jwtRefreshToken);
    }
}
