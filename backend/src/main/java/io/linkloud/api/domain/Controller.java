package io.linkloud.api.domain;

import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            throw new LogicException(ExceptionCode.TEMPORARY_ERROR);
        return "error";
    }
}
