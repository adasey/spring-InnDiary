package com.diary.inn.InnDiary.info.controller;

import com.diary.inn.InnDiary.login.LoginSession;
import com.diary.inn.InnDiary.login.user.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/home")
@Controller
public class HomeController {

    @RequestMapping
    public String goHome(Model model, @LoginSession SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
}
