package com.diary.inn.InnDiary.work.controller.api.oauth;

import com.diary.inn.InnDiary.login.LoginSession;
import com.diary.inn.InnDiary.login.user.entity.UserEntity;
import com.diary.inn.InnDiary.login.user.repository.UserRepository;
import com.diary.inn.InnDiary.login.user.service.UserService;
import com.diary.inn.InnDiary.login.user.SessionUser;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final MemberService memberService;

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
        Optional<UserEntity> packedEntity = userRepository.findByEmail(user.getEmail());
        HttpSession session = request.getSession();
        String result = loginSuccessOrFail(packedEntity);

        String isFail = "isFail";

        if (result.equals("home")) {
            if (session.getAttribute(isFail) != null) {
                session.removeAttribute(isFail);
            }
        }
        else {
            session.setAttribute(isFail, true);
        }

        return "redirect:/" + result;
    }

    private String loginSuccessOrFail(Optional<UserEntity> login) {
        if (login.isPresent()) {
            UserEntity user = login.get();

            try {
                memberService.findByEmail(user.getEmail());
                return "home";
            } catch (Exception exe) {
                log.info("등록되지 않은 사용자의 로그인 시도입니다.", exe);
                userService.userLoginFail(user.getEmail());
                return "logout";
            }
        }
        return "logout";
    }
}
