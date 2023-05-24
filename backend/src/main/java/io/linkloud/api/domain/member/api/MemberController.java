package io.linkloud.api.domain.member.api;


import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<SingleDataResponse<MemberLoginResponse>> findMe(
        @NonNull @LoginMemberId Long loginMemberId) {
        MemberLoginResponse memberLoginResponse = memberService.fetchPrincipal(loginMemberId);
        return ResponseEntity.ok(new SingleDataResponse<>(memberLoginResponse));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNickname(
        @LoginMemberId @NonNull Long loginMemberId,
        @RequestBody @Valid MemberNicknameRequestDto requestNicknameDto
    ) {
        memberService.updateNickname(loginMemberId,requestNicknameDto);
        return ResponseEntity.noContent().build();
    }

}
