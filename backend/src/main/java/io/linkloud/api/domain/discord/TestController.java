package io.linkloud.api.domain.discord;

import io.linkloud.api.domain.discord.service.DiscordWebhookService;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discord")
public class TestController {

    private final DiscordWebhookService webhookMain;

    @GetMapping("/500")
    public ResponseEntity<String> error500() {
        throw new IllegalArgumentException("500에러");
    }
}
