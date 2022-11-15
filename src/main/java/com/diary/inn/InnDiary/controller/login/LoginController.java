package com.diary.inn.InnDiary.controller.login;

import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.utils.login.session.LoginSession;
import com.diary.inn.InnDiary.service.login.UserService;
import com.diary.inn.InnDiary.domain.login.SessionUser;
import com.diary.inn.InnDiary.service.diary.SlotService;
import com.diary.inn.InnDiary.service.firebase.FirebaseDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
@Slf4j
public class LoginController {

    @GetMapping
    public String toLogin() {
        return "account/login";
    }

    @RequestMapping("/login/success")
    public String successLogin() {
        return "redirect:/";
    }
}
