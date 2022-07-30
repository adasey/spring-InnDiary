package com.diary.inn.InnDiary.work.controller.api.json;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonConversionService;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonService;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* rest api controller
* it is request and response api
* */
@Slf4j // for test
@RequestMapping("/inn/apis")
@RequiredArgsConstructor
@RestController
public class JsonResponseController {
    private final MemberService memberService;
    private final DiaryJsonService diaryJsonService;

    public ObjectMapper objectMapper = new ObjectMapper();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send/diary")
    public List<DiaryJson> diaryRequest(@RequestParam("email") String loginId, @RequestParam("company") int company) throws JsonProcessingException {
        Member findMember = memberService.findByEmailNCompany(loginId, company);

        return diaryJsonService.userDataFindAll(findMember);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send/todo")
    public List<ToDoJson> toDoRequest() throws JsonProcessingException {
        List<ToDoJson> data = new ArrayList<>();

        String jsonTest = "[{ " +
                "\"title\" : \"test\", " +
                "\"date\" : \"20220707\", " +
                "\"weather\" : 0, " +
                "\"status\" : 0, " +
                "\"content\" : \"test for\"" +
                "}, " +
                "{ " +
                "\"title\" : \"test\", " +
                "\"date\" : \"20220707\", " +
                "\"weather\" : 0, " +
                "\"status\" : 0, " +
                "\"content\" : \"test for\"" +
                "}]";

        ToDoJson toDoJson = ToDoJson.builder()
                .saveTitle("test")
                .memberId("test@test.com")
                .todo(jsonTest)
                .modDate(LocalDateTime.now())
                .build();

        data.add(toDoJson);

        return data;
    }

    @ResponseBody
    @PostMapping("/send/diary")
    public DiaryJson diaryResponse(@RequestBody DiaryJson diaryJson) {
        Member findMember = memberService.findByEmailNCompany(diaryJson.getMemberId(), diaryJson.getMemberCompany());

        DiaryJson findDiary = diaryJsonService.userDataFindWithSave(findMember, diaryJson.getSaveTitle());

        if (!diaryJson.getDiary().equals(findDiary.getDiary())) {
            diaryJsonService.uploadToJson(findDiary.getSeq(), diaryJson.getDiary());
        }
        if (!diaryJson.getSaveTitle().equals(findDiary.getSaveTitle())) {
            diaryJsonService.uploadToSaveTitle(findDiary.getSeq(), diaryJson.getSaveTitle());
        }

        return diaryJsonService.userDataFindWithSave(findMember, diaryJson.getSaveTitle());
    }

    @ResponseBody
    @PostMapping("/send/todo")
    public ToDoJson todoResponse(ToDoJson toDoJson) {
        log.info("helloData = {}", toDoJson.getMemberId());

        return toDoJson;
    }
}
