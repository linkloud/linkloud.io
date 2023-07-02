package io.linkloud.api.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {


    @RequestMapping(value = "/error")
    public String errorPages() {
        return "redirect:/404";
    }

}
