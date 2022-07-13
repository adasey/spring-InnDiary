package com.diary.inn.InnDiary.work.controller.api.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping
    public String defaultLogin() {
        return "oauth/login";
    }

    @RequestMapping("/oauth")
    public String toLogin() {
        return "oauth/login";
    }

    @PostMapping("/oauth/login/success")
    public String successLogin() {
        return "redirect:/home";
    }
}
