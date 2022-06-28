package com.diary.inn.InnDiary.api.controller.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping
    public String login() {
        return "login/login";
    }

    @RequestMapping("/login/oauth/google")
    public String oAuthGoogleLogin() {
        return "";
    }

    @RequestMapping("/login/oauth/kakao")
    public String oAuthKakaoLogin() {
        return "";
    }
}
