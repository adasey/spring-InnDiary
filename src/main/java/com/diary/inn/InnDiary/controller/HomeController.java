package com.diary.inn.InnDiary.controller;

import com.diary.inn.InnDiary.utils.login.session.LoginSession;
import com.diary.inn.InnDiary.domain.login.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class HomeController {

    @GetMapping
    public String goHome(Model model, @LoginSession SessionUser sessionUser) {
        model.addAttribute("sessionUser", sessionUser);
        return "index";
    }
}
