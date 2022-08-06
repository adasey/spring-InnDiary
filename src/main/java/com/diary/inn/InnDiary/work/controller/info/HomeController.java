package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.LoginSession;
import com.diary.inn.InnDiary.login.user.SessionUser;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/home")
@Controller
public class HomeController {
    private final MemberService memberService;

    @RequestMapping
    public String goHome(Model model, @LoginSession SessionUser sessionUser) {
        String email = null;
        int company = 0;

        if (sessionUser != null) {
            email = sessionUser.getEmail();
            company = sessionUser.getCompany();
        }
        else {
            return "redirect:/logout";
        }

        if (email != null) {
            memberService.findByEmailNCompany(email, company);
        }

        return "index";
    }
}
