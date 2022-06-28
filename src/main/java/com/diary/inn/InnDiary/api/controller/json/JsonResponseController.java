package com.diary.inn.InnDiary.api.controller.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* rest api controller
* it send api
* */
@Slf4j // for test
@RestController
@RequestMapping("/inn/api")
public class JsonResponseController {

    private ObjectMapper objectMapper;
}
