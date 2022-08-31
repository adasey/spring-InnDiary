package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.login.domain.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/home")
@Controller
public class HomeController {

    @RequestMapping
    public String goHome(Model model, @LoginSession SessionUser sessionUser) {
//        String email = null;
//        int company = 0;
//
//        if (sessionUser != null) {
//            email = sessionUser.getEmail();
//            company = sessionUser.getCompany();
//        }
//        else {
//            return "redirect:/logout";
//        }

        return "index";
    }
}
