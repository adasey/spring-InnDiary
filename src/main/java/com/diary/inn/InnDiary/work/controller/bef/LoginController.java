package com.diary.inn.InnDiary.work.controller.bef;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.login.service.UserService;
import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.work.domain.*;
import com.diary.inn.InnDiary.work.repository.firebase.FirebaseJsonRepository;
import com.diary.inn.InnDiary.work.service.data.DiaryService;
import com.diary.inn.InnDiary.work.service.data.SlotService;
import com.diary.inn.InnDiary.work.service.data.TodoService;
import com.diary.inn.InnDiary.work.service.json.DiaryFirebaseJsonService;
import com.diary.inn.InnDiary.work.service.json.TodoFirebaseJsonService;
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
    private final FirebaseJsonRepository firebaseJsonRepository;
    private final DiaryFirebaseJsonService diaryFirebaseJsonService;
    private final TodoFirebaseJsonService todoFirebaseJsonService;

    private final SlotService slotService;
    private final DiaryService diaryService;
    private final TodoService todoService;

    @RequestMapping
    public String defaultLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("failToLogin", false);
        return "oauth/login";
    }

    @RequestMapping("/oauth")
    public String toLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("failToLogin", true);
        return "oauth/login";
    }

    @RequestMapping("/oauth/login/success")
    public String successLogin(@LoginSession SessionUser sessionUser, HttpServletRequest request) {
        log.info("value check after login : {}", sessionUser.getUid());

        HttpSession session = request.getSession();
        session.removeAttribute("failToLogin");

        return "redirect:/home";
    }

    private void startLoginWebSetting(SessionUser sessionUser) {
        User foundUser = userService.findUserByEmail(sessionUser.getEmail());

        firebaseJsonRepository.setValueByUid(sessionUser.getUid());
        List<Slot> allSlotByUser = slotService.findAllSlotByUser(foundUser);
    }

    private void userBaseSlotSetting(List<Slot> asData) {
        if (isUserHasSlot(asData)) {
            for (Slot slot : asData) {
                slotService.deleteSlotWithDiaryNTodo(slot);
            }
        }

        List<Slot> diaryAllSlot = diaryFirebaseJsonService.processAllSlotFromFirebaseData(foundUser);
        List<Slot> todoAllSlot = todoFirebaseJsonService.processAllSlotFromFirebaseData(foundUser);

        for (Slot s : diaryAllSlot) {
            slotService.createSlot(s);
        }

        for (Slot s : todoAllSlot) {
            slotService.createSlot(s);
        }

        Map<Integer, List<DiaryMeta>> diaryWithSlot = diaryFirebaseJsonService.getDiaryWithSlot();
        Map<Integer, List<TodoMeta>> todoWithSlot = todoFirebaseJsonService.getTodoWithSlot();

        for (int i = 1; i <= 3; i++) {
            if (diaryWithSlot.get(i) != null) {
                Slot ds = slotService.findWhichSlotByNum(foundUser, "diary", i);
                for (DiaryMeta dm : diaryWithSlot.get(i)) {
                    Diary d = Diary.builder()
                            .diarySeq(dm.getSeq())
                            .title(dm.getTitle())
                            .status(dm.getStatus())
                            .weather(dm.getWeather())
                            .date(stringToDate(dm.getDate()))
                            .content(dm.getContent())
                            .slotSeq(ds.getSeq())
                            .build();

                    diaryService.createDiary(d);
                }
            }
        }

        for (int i = 1; i <= 3; i++) {
            if (todoWithSlot.get(i) != null) {
                Slot ts = slotService.findWhichSlotByNum(foundUser, "todo", i);
                for (TodoMeta tm : todoWithSlot.get(i)) {
                    Todo t = Todo.builder()
                            .todoSeq(tm.getSeq())
                            .title(tm.getTitle())
                            .date(stringToDate(tm.getDate()))
                            .content(tm.getContent())
                            .slotSeq(ts.getSeq())
                            .build();

                    todoService.createTodo(t);
                }
            }
        }
    }

    private void slotDeleteIfExist() {
        
    }

    private LocalDate stringToDate(String t) {
        LocalDate time = null;

        if (t.length() > 8) {
            time = LocalDate.parse(t, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }
        else {
            time = LocalDate.parse(t, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return time;
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
}
