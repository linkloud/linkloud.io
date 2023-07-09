package io.linkloud.api.domain.discord.service;

import io.linkloud.api.domain.discord.entity.DiscordWebhook;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import java.awt.Color;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DiscordWebhookService {

    @Value("${discord.url}")
    private String url;

    public void sendMessage(Exception e,HttpServletRequest req) {
        log.info("sending a message to the discord because server 500 error");
        DiscordWebhook webhook = new DiscordWebhook(url);

        String RequestURL = req.getRequestURL().toString();
        String RequestMethod = req.getMethod();
        String RequestTime = new Date().toString();
        String RequestIP = req.getRemoteAddr();
        String RequestUserAgent = req.getHeader("User-Agent");

        StackTraceElement[] stackTrace = e.getStackTrace();
        String stackTraceInfo = null;
        if (stackTrace.length > 0) {
            StackTraceElement firstElement = stackTrace[0];
            stackTraceInfo = firstElement.getClassName() + "." + firstElement.getMethodName() + "(" + firstElement.getFileName() + ":" + firstElement.getLineNumber() + ")";
        }

        webhook.addEmbed(new DiscordWebhook.EmbedObject()
            .setTitle("** 에러 내용 **")
            .setDescription(e.getMessage())
            .setColor(new Color(16711680))
            .addField("HttpMethod",RequestMethod,false)
            .addField("REQUEST_ENDPOINT",RequestURL,false)
            .addField("CLIENT_IP",RequestIP,false)
            .addField("ERROR_STACK",stackTraceInfo,false)
            .addField("TIME",RequestTime,false)
            .addField("USER_AGENT",RequestUserAgent,false));

        try{
            webhook.execute();
        } catch (IOException exception) {
            throw new CustomException(LogicExceptionCode.JSON_REQUEST_FAILED);
        }
    }
}
