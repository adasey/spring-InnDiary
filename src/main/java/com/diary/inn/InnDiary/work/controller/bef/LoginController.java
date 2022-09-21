package com.diary.inn.InnDiary.work.controller.bef;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.login.service.UserService;
import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.work.domain.*;
import com.diary.inn.InnDiary.work.service.data.SlotService;
import com.diary.inn.InnDiary.work.service.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;
    private final SlotService slotService;

    private final FirebaseService firebaseService;

    @RequestMapping
    public String defaultLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("failToLogin", false);
        return "oauth/login";
    }

    @RequestMapping("/oauth")
    public String toLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginSuccess") != null) {
            session.removeAttribute("loginSuccess");
            session.setAttribute("failToLogin", false);
        }
        else {
            session.setAttribute("failToLogin", true);
        }
        return "oauth/login";
    }

    @RequestMapping("/oauth/login/success")
    public String successLogin(@LoginSession SessionUser sessionUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("failToLogin");
        session.setAttribute("loginSuccess", true);

        startLoginWebSetting(sessionUser);

        return "redirect:/home";
    }

    private void startLoginWebSetting(SessionUser sUser) {
        User foundUser = userService.findUserByEmail(sUser.getEmail());

        firebaseService.setDataSnapByUid(sUser.getUid());
        List<Slot> allSlotByUser = slotService.findAllSlotByUser(foundUser);

        if (!isUserHasSlot(allSlotByUser)) {
            userSlotSetting(foundUser);
        }
    }

    private boolean isUserHasSlot(List<Slot> slots) {
        for (Slot s : slots) {
            if (isExistVal(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistVal(Slot s) {
        return s != null;
    }

    private void userSlotSetting(User found) {
        firebaseService.insertAllSlots(found);
        firebaseService.createAllSlotsData(found);
    }
}
