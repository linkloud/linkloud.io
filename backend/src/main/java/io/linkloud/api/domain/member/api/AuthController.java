package io.linkloud.api.domain.member.api;

import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<AuthResponseDto> authenticate(
        @RequestBody AuthRequestDto dto, HttpServletResponse response) {
        AuthResponseDto responseDto = authService.authenticate(dto,response);
        log.info("successfully created the first accessToken");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshAccessToken(
        @CookieValue("refreshToken") String refreshToken,
        HttpServletResponse response) {
        AuthResponseDto responseDto = authService.refreshTokenAndAccessToken(refreshToken,response);
        log.info("successfully remade the accessToken by refreshToken");
        return ResponseEntity.ok(responseDto);
    }
}
