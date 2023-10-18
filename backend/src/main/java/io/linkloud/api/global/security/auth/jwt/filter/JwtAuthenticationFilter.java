package io.linkloud.api.global.security.auth.jwt.filter;

import io.jsonwebtoken.Claims;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.security.auth.handler.ErrorResponseUtil;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import io.linkloud.api.global.security.auth.jwt.JwtTokenType;
import io.linkloud.api.global.security.auth.jwt.dto.SecurityMember;
import io.linkloud.api.global.security.auth.jwt.utils.HeaderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Access Token 의 유효성을 확인하는 필터
 * 1. Header 검증 후 Jwt Token 추출
 * 2. 토큰이 유효한지 검증
 * 3. 유효하다면 사용자 정보 추출
 * 4. 사용자 정보 스프링 시큐리티 컨텍스트에 등록
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final MemberRepository memberRepository;
    private static final String REFRESH_TOKEN_URI = "/api/v1/auth/refresh";


    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();


       if (requestURI.equals(REFRESH_TOKEN_URI)) {
           log.info("리프레시토큰요청");
           Cookie[] cookies = request.getCookies();
           if (cookies != null) {
               for (Cookie cookie : cookies) {
                   if (cookie.getName().equals("refreshToken")) {
                       String refreshToken = cookie.getValue();
                       try {
                           log.info("filter.refreshToken.validateToken() ");
                           jwtProvider.validateToken(refreshToken, JwtTokenType.REFRESH_TOKEN);
                       } catch (CustomException e) {
                           log.error("refreshToken 이 유효하지 않습니다. 쿠키를 제거합니다");
                           cookie.setValue("");
                           cookie.setPath("/");
                           cookie.setMaxAge(0);
                           response.addCookie(cookie);
                           ErrorResponseUtil.sendErrorResponse(response, e);
                           return;
                       }
                   }
               }
           }
           log.info("정상적인 refreshToken 입니다");
           filterChain.doFilter(request, response);
           return;
        }

        // 1. Header 검증 후 Jwt Token 추출
        log.info("JWT 인증 필터 실행 = {}", HeaderUtil.getAccessToken(request));
        String accessToken = HeaderUtil.getAccessToken(request);


        try {
            // 2. 토큰이 유효한지 검증
            if (accessToken != null && jwtProvider.validateToken(accessToken,JwtTokenType.ACCESS_TOKEN)) {
                log.info("토근이 유효합니다.");

                // 3. 사용자 정보 추출
                String memberId = jwtProvider.getClaims(accessToken, JwtTokenType.ACCESS_TOKEN, Claims::getId);
                Member member = memberRepository.findById(Long.valueOf(memberId)).orElseThrow(
                    () -> new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND)
                );

                // 4. 사용자 정보 스프링 시큐리티 컨텍스트에 등록
                Authentication authentication = setUserDetails(member);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);


            }

        } catch (CustomException e) {
            log.error("JWT error: {}", e.getMessage());
            ErrorResponseUtil.sendErrorResponse(response, e);
            return;
        }
        log.info("JWT 인증 필터 종료");
        filterChain.doFilter(request, response);
    }
    private Authentication setUserDetails(Member member) {
        UserDetails userDetails = new SecurityMember(
            member.getId(),
            member.getNickname(),
                List.of(new SimpleGrantedAuthority(member.getRole().getKey())),
            member.getPicture(),
            member.getSocialType()
        );
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }
}
