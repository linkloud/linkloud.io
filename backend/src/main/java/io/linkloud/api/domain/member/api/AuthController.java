package io.linkloud.api.domain.member.api;

import io.linkloud.api.domain.member.dto.AuthRequestDto;
import io.linkloud.api.domain.member.dto.AuthResponseDto;
import io.linkloud.api.global.security.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<AuthResponseDto> authenticate(
        @RequestBody AuthRequestDto dto) {
        AuthResponseDto responseDto = authService.authenticate(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
