package io.linkloud.api.global.security.auth.service;

import io.jsonwebtoken.Claims;
import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.domain.member.dto.CreateRefreshTokenRequestDto;
import io.linkloud.api.domain.member.dto.MemberSignUpResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.domain.member.service.RefreshTokenService;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.security.auth.client.OAuthClient;
import io.linkloud.api.global.security.auth.client.dto.OAuthAttributes;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthService {

    private final Map<String, OAuthClient> oAuthClients;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


    /**
     * 사용자 인증하고 JWT 토큰 발급
     * 1. 인증 코드(code)를 이용해 access token 받기
     * 2. access token 을 이용해 사용자 정보 받기
     * 3. DB에 사용자 정보 없으면 저장, 있으면 사용자 바로 리턴
     * 4. JWT 토큰 발급 후 반환
     */
    public AuthResponseDto authenticate(AuthRequestDto dto,HttpServletResponse response) {
        OAuthClient oAuthClient = oAuthClients.get(dto.getSocialType() + "OAuthClientImpl");
        if (oAuthClient == null) {
            throw new CustomException(AuthExceptionCode.INVALID_SOCIAL_TYPE);
        }
        // 1
        String oAuthAccessToken = oAuthClient.getAccessToken(dto.getCode());

        // 2
        OAuthAttributes userInfo = oAuthClient.getUserInfo(oAuthAccessToken);

        // 3
        MemberSignUpResponseDto memberDto = memberService.signUpIfNotExists(userInfo);

        Long memberId = memberDto.getId();
        String jwtAccessToken = jwtProvider.generateAccessToken(memberId,memberDto.getSocialType());
        String jwtRefreshToken = jwtProvider.generateRefreshToken(memberId);

        Cookie cookie = new Cookie("refreshToken",jwtRefreshToken);
        cookie.setMaxAge(1000); // todo: 쿠키 만료시간 변경
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        refreshTokenService.createRefreshToken(new CreateRefreshTokenRequestDto(
            memberId,
            jwtRefreshToken
        ));

        // 4
        // TODO : refreshToken 리턴 안해도 됨. 쿠키에 refreshToken 보냄
        return new AuthResponseDto(jwtAccessToken);
    }

    public AuthResponseDto refreshTokenAndAccessToken(String refreshToken,HttpServletResponse response) {


        Long memberId = Long.valueOf(jwtProvider.getClaims(refreshToken, Claims::getId));

        try {
            refreshTokenService.validateRefreshToken(memberId, refreshToken);
        } catch (CustomException e) {
            refreshTokenService.removeRefreshToken(memberId);
            throw new CustomException(AuthExceptionCode.AUTHORIZED_FAIL);
        }

        Member member = memberService.fetchMemberById(memberId);
        String newJwtAccessToken = jwtProvider.generateAccessToken(member.getId(),
            member.getSocialType());
        String newJwtRefreshToken = jwtProvider.generateRefreshToken(member.getId());

        Cookie cookie = new Cookie("refreshToken",newJwtRefreshToken);
        cookie.setMaxAge(1000); // todo: 쿠키 만료시간 변경
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        refreshTokenService.createRefreshToken(new CreateRefreshTokenRequestDto(
            member.getId(),
            newJwtRefreshToken
        ));

        return new AuthResponseDto(newJwtAccessToken);
    }
}
