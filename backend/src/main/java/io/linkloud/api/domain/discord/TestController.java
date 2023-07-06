package io.linkloud.api.domain.discord;

import io.linkloud.api.domain.member.DiscordWebhookService;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final DiscordWebhookService webhookMain;

    @GetMapping("/500")
    public ResponseEntity<String> error500() {
        throw new IllegalArgumentException("500에러");
    }



    @GetMapping("/404")
    public ResponseEntity<String> error400() {
        throw new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND);
    }





}
