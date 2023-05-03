package io.linkloud.api.domain.member.api;


import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.auth.jwt.dto.SecurityMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<SingleDataResponse<MemberLoginResponse>> loginSuccess(
        @AuthenticationPrincipal SecurityMember securityMember) {
        MemberLoginResponse principalDto = memberService.fetchPrincipal(securityMember);
        return ResponseEntity.ok(new SingleDataResponse<>(principalDto));
    }

}
