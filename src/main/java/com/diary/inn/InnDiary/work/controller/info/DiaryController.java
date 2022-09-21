package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.service.data.DiaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diaries")
@Slf4j
public class DiaryController {
    private final DiaryService diaryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/calendars")
    private String getCalenderDiary(@LoginSession SessionUser sUser, HttpSession session, Model model) {
        model.addAttribute("sUser", sUser);

        return "diary/diaryCalendar";
    }

    @GetMapping("/calendars/values/now")
    @ResponseBody
    private JsonNode callDiarySearch(@RequestParam String start, @RequestParam String end) throws JsonProcessingException {
        LocalDate startDate = stringToLocalDate(start);
        LocalDate endDate = stringToLocalDate(end);
        List<Diary> monthDiary = diaryService.findBetweenMonthDiary(startDate, endDate);

        return objectMapper.readTree(diaryToDiaryJson(monthDiary).toString());
    }

    private LocalDate stringToLocalDate(String time) {
        String[] ts = time.split("T");
        LocalDate ldt = null;

        if (ts[1].equals("00:00:00+09:00")) {
            ldt = LocalDate.parse(ts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        else {
            String parsedTime = ts[0] + "T" + parseStringTimeToLocalDate(ts[1]);
            ldt = LocalDate.parse(parsedTime, DateTimeFormatter.ofPattern("yyyy-MM-ddTHHmm"));
        }

        return ldt;
    }

    private String parseStringTimeToLocalDate(String time) {
        String[] onlyTime = time.split("\\+");
        String[] hourMinSec = onlyTime[0].split(":");

        return hourMinSec[0] + hourMinSec[1];
    }

    private List<String> diaryToDiaryJson(List<Diary> diaries) throws JsonProcessingException {
        List<String> result = new ArrayList<>();
        Map<String, Object> dMap = new HashMap<>();

        for (Diary d : diaries) {
            dMap.put("id", d.getDiarySeq().toString());
            dMap.put("title", d.getTitle());
            dMap.put("url", "/diaries/" + d.getDiarySeq());
            dMap.put("start", d.getDate().toString());

            result.add(objectMapper.writeValueAsString(dMap));
            dMap.clear();
        }
        return result;
    }

    @GetMapping("/lists")
    private String getListDiary(@LoginSession SessionUser sUser, Model model) {
        model.addAttribute("sUser", sUser);

        List<Diary> nowDiary = diaryService.findMonthDiary(LocalDate.now());
        model.addAttribute("diaries", nowDiary);

        return "diary/diaryList";
    }

    @GetMapping("/{id}")
    private String getListDiary(@LoginSession SessionUser sUser, @PathVariable Long id, Model model) {
        model.addAttribute("sUser", sUser);

        Diary diary = diaryService.findDiaryBySeq(id);
        model.addAttribute("diary", diary);

        return "diary/diaryDetail";
    }

//    @GetMapping("/calendars/values/fix")
//    @ResponseBody
//    private JsonNode callDiarySixMonth(@RequestParam String date) throws JsonProcessingException {
//        LocalDate localDate = stringToLocalDate(date);
//        List<Diary> sixMonthDiary = diaryService.findSixMonthDiary(localDate);
//
//        return objectMapper.readTree(diaryToDiaryJson(sixMonthDiary).toString());
//    }
//
//    @GetMapping("/calendars/values/range")
//    @ResponseBody
//    private JsonNode callDiaryManyMonth(@RequestParam String startDate, @RequestParam String endDate) throws JsonProcessingException {
//        LocalDate sDate = stringToLocalDate(startDate);
//        LocalDate eDate = stringToLocalDate(endDate);
//        List<Diary> betweenMonthDiary = diaryService.findBetweenMonthDiary(sDate, eDate);
//
//        return objectMapper.readTree(diaryToDiaryJson(betweenMonthDiary).toString());
//    }
}
