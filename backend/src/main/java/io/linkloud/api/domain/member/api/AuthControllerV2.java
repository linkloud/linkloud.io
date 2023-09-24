package io.linkloud.api.domain.member.api;

import io.linkloud.api.domain.member.dto.AuthV2Dto;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AccessTokenRequest;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AccessTokenResponse;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AuthRequest;
import io.linkloud.api.domain.member.dto.AuthV2Dto.AuthResponse;
import io.linkloud.api.global.security.auth.service.AuthServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
@RestController
public class AuthControllerV2 {

    private final AuthServiceV2 authServiceV2;

    // 회원 로그인 또는 회원가입
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest requestDto) {
        AuthResponse memberId = authServiceV2.authenticate(requestDto);
        return ResponseEntity.ok(memberId);
    }

    // 회원정보로 accessToken 생성
    @PostMapping("/accessToken")
    public ResponseEntity<AccessTokenResponse> accessToken(@RequestBody AccessTokenRequest tokenRequest) {
        AccessTokenResponse accessToken = authServiceV2.createAccessToken(tokenRequest);
        return ResponseEntity.ok(accessToken);
    }
}
