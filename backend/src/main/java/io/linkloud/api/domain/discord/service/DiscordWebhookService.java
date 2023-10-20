package io.linkloud.api.domain.discord.service;

import io.linkloud.api.domain.discord.entity.DiscordWebhook;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.security.auth.jwt.utils.HeaderUtil;
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

        // nginx proxy ip 가져오기
        String clientIP = HeaderUtil.getClientProxyIP(req);

        String requestURL = req.getRequestURL().toString();
        String requestMethod = req.getMethod();
        String requestTime = new Date().toString();
//        String RequestIP = req.getRemoteAddr();
        String requestUserAgent = req.getHeader("User-Agent");

        log.info("send those client's information to Discord");
        log.info("client IP: {}", clientIP);
        log.info("requestURL : {}", requestURL);
        log.info("requestMethod : {}", requestMethod);
        log.info("requestUserAgent : {}", requestUserAgent);
        log.info("requestTime : {}",requestTime);
        log.info("=============================================");

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
            .addField("HTTP_METHOD", requestMethod, false)
            .addField("REQUEST_ENDPOINT", requestURL, false)
            .addField("CLIENT_IP", clientIP, false)
            .addField("ERROR_STACK", stackTraceInfo, false)
            .addField("TIME", requestTime, false)
            .addField("USER_AGENT", requestUserAgent, false));

        try{
            webhook.execute();
        } catch (IOException exception) {
            throw new CustomException(LogicExceptionCode.JSON_REQUEST_FAILED);
        }
    }
}
