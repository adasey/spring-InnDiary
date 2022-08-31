package com.diary.inn.InnDiary.work.controller.bef;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.login.repository.UserRepository;
import com.diary.inn.InnDiary.login.service.UserService;
import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.work.domain.bef.DiarySlot;
import com.diary.inn.InnDiary.work.repository.bef.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;

    @RequestMapping
    public String defaultLogin() {
        return "oauth/login";
    }

    @RequestMapping("/oauth")
    public String toLogin() {
        return "oauth/login";
    }

    @RequestMapping("/oauth/login/success")
    public String successLogin(@LoginSession SessionUser user, HttpServletRequest request) {
        User foundUser = userService.findUserByEmail(user.getEmail());

        return "redirect:/home";
    }
}