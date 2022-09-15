package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.login.domain.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/home")
@Controller
public class HomeController {

    @RequestMapping
    public String goHome(Model model, @LoginSession SessionUser sessionUser) {
        model.addAttribute("sUser", sessionUser);
        return "index";
    }
}
