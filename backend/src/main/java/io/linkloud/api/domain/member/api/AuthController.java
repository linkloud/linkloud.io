package io.linkloud.api.domain.member.api;

import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.domain.member.dto.RefreshAccessTokenRequest;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;


    @PostMapping("/{socialType}")
    public ResponseEntity<SingleDataResponse<AuthResponseDto>> authenticate(
        @RequestBody AuthRequestDto dto) {
        AuthResponseDto responseDto = authService.authenticate(dto);
        log.info("첫 토큰 생성");
        log.info("AccessToken={}",responseDto.getAccessToken());
        log.info("RefreshToken={}",responseDto.getRefreshToken());
        return ResponseEntity.ok(new SingleDataResponse<>(responseDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshAccessToken(@RequestBody
    RefreshAccessTokenRequest dto) {
        AuthResponseDto responseDto = authService.refreshTokenAndAccessToken(dto.getRefreshToken(),
            dto.getTokenType());
        log.info("토큰 재생성");
        log.info("AccessToken={} ",responseDto.getAccessToken());
        log.info("RefreshToken={} ",responseDto.getRefreshToken());
        return ResponseEntity.ok(responseDto);
    }
}
