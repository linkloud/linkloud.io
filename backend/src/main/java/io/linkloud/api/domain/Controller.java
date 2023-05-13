package io.linkloud.api.domain;

import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String landingPage() {
        return "Hello!";
    }

    @GetMapping("/hello")
    public String errorPage() {
        if(true)
            throw new CustomException(LogicExceptionCode.TEMPORARY_ERROR);
        return "error";
    }
}
