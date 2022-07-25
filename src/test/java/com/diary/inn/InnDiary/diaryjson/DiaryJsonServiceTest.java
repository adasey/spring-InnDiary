package com.diary.inn.InnDiary.diaryjson;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonConversionService;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonProcess;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
public class DiaryJsonServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    DiaryJsonService diaryJsonService;
    @Autowired
    DiaryJsonConversionService diaryJsonConversionService;
    @Autowired
    DiaryJsonProcess diaryJsonProcess;

    public ObjectMapper objectMapper = new ObjectMapper();

    public String JsonGenerator(int id) throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "test" + id);
        map.put("reg_date", "20220707" + id);
        map.put("weather", 0);
        map.put("status", 0);
        map.put("content", "test " + id);
        return objectMapper.writeValueAsString(map);
    }

    public String LotsJsonGenerator(int id) throws JsonProcessingException {
        List<HashMap<String, Object>> maps = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "test" + id);
            map.put("reg_date", "20220707" + id);
            map.put("weather", 0);
            map.put("status", 0);
            map.put("content", "test " + id);

            maps.add(map);
        }

        return objectMapper.writeValueAsString(maps);
    }

    @BeforeEach
    @Transactional
    void saveMember() {
        Member member = Member.builder()
                .loginId("test")
                .company(0)
                .state(0)
                .build();
        memberService.join(member);
    }
}