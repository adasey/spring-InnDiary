package com.diary.inn.InnDiary.work.controller.api.json;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        return data;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send/todo")
    public List<ToDoJson> toDoRequest() throws JsonProcessingException {
        List<ToDoJson> data = new ArrayList<>();

        return data;
    }

    @ResponseBody
    @PostMapping("/send/diary")
    public DiaryJson diaryResponse(@RequestBody DiaryJson diaryJson) {

        return diaryJson;
    }

    @ResponseBody
    @PostMapping("/send/todo")
    public ToDoJson todoResponse(ToDoJson toDoJson) {

        return toDoJson;
    }
}
