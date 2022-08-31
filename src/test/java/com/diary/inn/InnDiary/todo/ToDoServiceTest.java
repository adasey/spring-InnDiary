//package com.diary.inn.InnDiary.todo;
//
//import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
//import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
//import com.diary.inn.InnDiary.work.domain.info.Member;
//import com.diary.inn.InnDiary.work.repository.api.ToDoJsonRepository;
//import com.diary.inn.InnDiary.work.repository.info.MemberRepository;
//import com.diary.inn.InnDiary.work.repository.info.ToDoRepository;
//import com.diary.inn.InnDiary.work.service.api.ToDoJsonService;
//import com.diary.inn.InnDiary.work.service.info.MemberService;
//import com.diary.inn.InnDiary.work.service.info.ToDoService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@SpringBootTest
//@Slf4j
//class ToDoServiceTest {
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    ToDoJsonService toDoJsonService;
//    @Autowired
//    ToDoService toDoService;
//
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    ToDoJsonRepository toDoJsonRepository;
//    @Autowired
//    ToDoRepository toDoRepository;
//
//    public ObjectMapper objectMapper = new ObjectMapper();
//
//    public String JsonGenerator(int id) throws JsonProcessingException {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("seq", id);
//        map.put("title", "test" + id);
//        map.put("reg_date", "20220707" + id);
//        map.put("weather", 0);
//        map.put("status", 0);
//        map.put("content", "test " + id);
//        return objectMapper.writeValueAsString(map);
//    }
//
//    public String LotsJsonGenerator(int id) throws JsonProcessingException {
//        List<HashMap<String, Object>> maps = new ArrayList<>();
//
//        for (int i = 1; i <= 3; i++) {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("seq", id);
//            map.put("title", "test" + id);
//            map.put("reg_date", "20220707" + id);
//            map.put("weather", 0);
//            map.put("status", 0);
//            map.put("content", "test " + id);
//
//            maps.add(map);
//        }
//
//        return objectMapper.writeValueAsString(maps);
//    }
//
//    @BeforeEach
//    void memberAndDiaryJsonInsert() {
//    }
//
//    @Test
//    void entityToDto() {
//    }
//
//    @Test
//    void dtoToEntity() {
//    }
//
//    @Test
//    void toDoJoin() {
//    }
//
//    @Test
//    void toDoSaveAll() {
//        Member member = memberService.findByEmailNCompany("lmo9903@gmail.com", 0);
//        ToDoJson toDoJson = toDoJsonService.userDataFindWithSave(member, "test3");
//
//        log.info("test of found value : {}", toDoJson);
//
//        toDoService.toDoSaveAll(toDoJson);
//    }
//}