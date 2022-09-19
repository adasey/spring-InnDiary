package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.service.UserService;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.work.domain.*;
import com.diary.inn.InnDiary.work.service.data.DiaryService;
import com.diary.inn.InnDiary.work.service.data.SlotService;
import com.diary.inn.InnDiary.work.service.data.TodoService;
import com.diary.inn.InnDiary.work.service.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/slots")
@Slf4j
public class SlotController {
    private final SlotService slotService;

    private final FirebaseService firebaseService;
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

//    @ResponseBody
    @GetMapping("/diaries/call")
    private String callDiarySlot(@LoginSession SessionUser sUser, @RequestParam Integer slotNum) {
        User user = userService.findUserByEmail(sUser.getEmail());
        firebaseService.processDiary(user);

        firebaseService.createDiaryData(user, slotNum);

        Slot dSlot = slotService.findWhichSlotByNum(user, "diary", slotNum);
        diaryService.setWantSlot(dSlot);

        return "redirect:/slots";
    }

//    @ResponseBody
    @GetMapping("/todos/call")
    private String callTodoSlot(@LoginSession SessionUser sUser, @RequestParam Integer slotNum) {
        User user = userService.findUserByEmail(sUser.getEmail());
        firebaseService.processTodo(user);

        firebaseService.createTodoData(user, slotNum);

        Slot tSlot = slotService.findWhichSlotByNum(user, "todo", slotNum);
        todoService.setWantSlot(tSlot);

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

        firebaseService.setDataSnapByUid(sUser.getUid());
        List<Slot> allSlotByUser = slotService.findAllSlotByUser(user);

        if (isUserHasSlot(allSlotByUser)) {
            deleteSlotIfExist(allSlotByUser);
        }

        firebaseService.insertAllSlots(user);
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
