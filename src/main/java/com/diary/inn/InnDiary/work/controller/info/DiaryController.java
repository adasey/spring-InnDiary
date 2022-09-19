package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.service.data.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/calendars")
    private String getCalenderDiary(@LoginSession SessionUser sUser, Model model) {
        model.addAttribute("sUser", sUser);

        List<Diary> monthDiary = new ArrayList<>();

        if (diaryService.isSlotSetting()) {
            LocalDate now = LocalDate.now();
            monthDiary = diaryService.findMonthDiary(now);
            model.addAttribute("isSlotExist", true);
        }

        model.addAttribute("isSlotExist", false);
        model.addAttribute("monthDiary", monthDiary);

        return "diary/diaryCalendar";
    }

    @PostMapping("/calendars/values/fix")
    private String callDiarySixMonth(@RequestParam String date, Model model) {
        LocalDate localDate = stringToLocalDate(date);
        List<Diary> sixMonthDiary = diaryService.findSixMonthDiary(localDate);

        model.addAttribute("monthDiary", sixMonthDiary);

        return "diary/diaryCalendar";
    }

    @PostMapping("/calendars/values/range")
    private String callDiaryManyMonth(@RequestParam String startDate, @RequestParam String endDate, Model model) {
        LocalDate sDate = stringToLocalDate(startDate);
        LocalDate eDate = stringToLocalDate(endDate);
        List<Diary> betweenMonthDiary = diaryService.findBetweenMonthDiary(sDate, eDate);

        model.addAttribute("monthDiary", betweenMonthDiary);

        return "diary/diaryCalendar";
    }

    @GetMapping("/lists")
    private String getListDiary(@LoginSession SessionUser sUser, Model model) {
        model.addAttribute("sUser", sUser);
        return "diary/diaryList";
    }

    private LocalDate stringToLocalDate(String time) {
        LocalDate ldt = null;

        if (time.length() > 8) {
            ldt = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }
        else {
            ldt = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return ldt;
    }
}
