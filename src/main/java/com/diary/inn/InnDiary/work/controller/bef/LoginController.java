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

        startLoginWebSetting(sessionUser);

        return "redirect:/home";
    }

    private void startLoginWebSetting(SessionUser sUser) {
        User foundUser = userService.findUserByEmail(sUser.getEmail());

        firebaseJsonRepository.setValueByUid(sUser.getUid());
        List<Slot> allSlotByUser = slotService.findAllSlotByUser(foundUser);

//        slotDeleteIfExist(allSlotByUser);
        if (!isUserHasSlot(allSlotByUser)) {
            userBaseSlotSetting(foundUser);
        }
    }

    private void slotDeleteIfExist(List<Slot> asData) {
        if (isUserHasSlot(asData)) {
            for (Slot slot : asData) {
                slotService.deleteSlot(slot);

                diaryService.deleteDiaryBySlot(slot.getSeq());
                todoService.deleteTodoBySlot(slot.getSeq());
            }
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

    private void userBaseSlotSetting(User found) {
        slotDataInsertAll(found);
        callFirebaseDiaryNTodoNSaveAll(found);
    }

    private void slotDataInsertAll(User found) {
        List<Slot> dSlot = diaryFirebaseJsonService.processAllSlotFromFirebaseData(found);
        List<Slot> tSlot = todoFirebaseJsonService.processAllSlotFromFirebaseData(found);

        for (Slot s : dSlot) {
            slotService.createSlot(s);
        }

        for (Slot s : tSlot) {
            slotService.createSlot(s);
        }
    }

    private void callFirebaseDiaryNTodoNSaveAll(User found) {
        Map<Integer, List<DiaryMeta>> diaryWithSlot = diaryFirebaseJsonService.getDiaryWithSlot();
        Map<Integer, List<TodoMeta>> todoWithSlot = todoFirebaseJsonService.getTodoWithSlot();

        for (int i = 1; i <= 3; i++) {
            if (diaryWithSlot.get(i) != null) {
                Slot ds = slotService.findWhichSlotByNum(found, "diary", i);
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
                Slot ts = slotService.findWhichSlotByNum(found, "todo", i);
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
}
