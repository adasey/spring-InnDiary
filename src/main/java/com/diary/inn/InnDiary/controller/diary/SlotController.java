package com.diary.inn.InnDiary.controller.diary;

import com.diary.inn.InnDiary.domain.login.SessionUser;
import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.service.login.UserService;
import com.diary.inn.InnDiary.utils.login.session.LoginSession;
import com.diary.inn.InnDiary.service.diary.DiaryService;
import com.diary.inn.InnDiary.service.diary.SlotService;
import com.diary.inn.InnDiary.service.diary.TodoService;
import com.diary.inn.InnDiary.service.firebase.FirebaseDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/slots")
@Slf4j
public class SlotController {
    private final SlotService slotService;

    private final FirebaseDataService firebaseDataService;
    private final UserService userService;
    private final DiaryService diaryService;
    private final TodoService todoService;

    @GetMapping("")
    private String getAllSlotValue(@LoginSession SessionUser sUser, Model model) {
        model.addAttribute("sUser", sUser);

        List<Slot> diarySlots = slotService.findWhichSlotByUid(sUser.getUid(), "diary");
        List<Slot> todoSlots = slotService.findWhichSlotByUid(sUser.getUid(), "todo");

        model.addAttribute("diaryCase", fillEmptySpace(diarySlots));
        model.addAttribute("todoCase", fillEmptySpace(todoSlots));
        return "slot/slots";
    }

    @PostMapping("/update")
    private String updateAllSlot(@LoginSession SessionUser sUser) {
        updateAllSlots(sUser);

        return "redirect:/slots";
    }

    @GetMapping("/diaries/call")
    private String callDiarySlot(@LoginSession SessionUser sUser, @RequestParam Integer slotNum, HttpSession session) {
        User user = userService.findUserByEmail(sUser.getEmail());
        firebaseDataService.processDiary(user);

        firebaseDataService.createDiaryData(user, slotNum);

        Slot dSlot = slotService.findWhichSlotByNum(user, "diary", slotNum);
        session.setAttribute("diarySlot", slotNum);
        diaryService.setWantSlot(dSlot);

        return "redirect:/slots";
    }

    @GetMapping("/todos/call")
    private String callTodoSlot(@LoginSession SessionUser sUser, @RequestParam Integer slotNum, HttpSession session) {
        User user = userService.findUserByEmail(sUser.getEmail());
        firebaseDataService.processTodo(user);

        firebaseDataService.createTodoData(user, slotNum);

        Slot tSlot = slotService.findWhichSlotByNum(user, "todo", slotNum);
        todoService.setWantSlot(tSlot);
        session.setAttribute("todoSlot", slotNum);

        return "redirect:/slots";
    }

    private List<Slot> fillEmptySpace(List<Slot> slots) {
        List<Slot> result = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            result.add(null);

            for (Slot s : slots) {
                if (s.getSlotNum() == i) {
                    result.set(i - 1, s);
                    break;
                }
            }
        }

        return result;
    }

    private void updateAllSlots(SessionUser sUser) {
        User user = userService.findUserByEmail(sUser.getEmail());

        List<Slot> allSlotByUser = slotService.findAllSlotByUser(user);

        if (isUserHasSlot(allSlotByUser)) {
            deleteSlotIfExist(allSlotByUser);
        }

        firebaseDataService.insertAllSlots(user);
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

    private void deleteSlotIfExist(List<Slot> asData) {
        if (isUserHasSlot(asData)) {
            for (Slot slot : asData) {
                diaryService.deleteDiaryBySlot(slot.getSeq());
                todoService.deleteTodoBySlot(slot.getSeq());

                slotService.deleteSlot(slot);
            }
        }
    }
}
