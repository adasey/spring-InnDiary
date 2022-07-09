package com.diary.inn.InnDiary.api.controller.json;

import com.diary.inn.InnDiary.api.domain.DiaryJson;
import com.diary.inn.InnDiary.api.domain.ToDoJson;
import com.diary.inn.InnDiary.info.domain.Diary;
import com.diary.inn.InnDiary.info.domain.ToDo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* rest api controller
* it is request and response api
* */
@Slf4j // for test
@RequestMapping("/inn/apis")
@RequiredArgsConstructor
@RestController
public class JsonResponseController {
    public ObjectMapper objectMapper = new ObjectMapper();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send/diary")
    public List<DiaryJson> diaryRequest() throws JsonProcessingException {
        List<DiaryJson> data = new ArrayList<>();

        String jsonTest = "[{ \"title\" : \"test\", " +
                "\"date\" : \"20220707\", " +
                "\"weather\" : 0, " +
                "\"status\" : 0, " +
                "\"content\" : \"test for\"" +
        "}]";

        DiaryJson diaryJson = DiaryJson.builder()
                .save("1")
                .loginId("test@test.com")
                .Json(objectMapper.readTree(jsonTest))
                .modDate(LocalDateTime.now())
                .build();

        data.add(diaryJson);

        return data;
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
                "}]";

        ToDoJson toDoJson = ToDoJson.builder()
                .save("test")
                .loginId("test@test.com")
                .Json(objectMapper.readTree(jsonTest))
                .modDate(LocalDateTime.now())
                .build();

        data.add(toDoJson);

        return data;
    }

    @ResponseBody
    @PostMapping("/send/diary")
    public DiaryJson diaryResponse(@RequestBody DiaryJson diaryJson) {
        log.info("DiaryJson = {}", diaryJson.getLoginId());

        return diaryJson;
    }

    @ResponseBody
    @PostMapping("/send/todo")
    public ToDo todoResponse(ToDo toDo) {
        log.info("helloData = {}", toDo.getClass().getName());

        return toDo;
    }
}
