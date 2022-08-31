//package com.diary.inn.InnDiary.diary;
//
//import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
//import com.diary.inn.InnDiary.work.domain.info.Diary;
//import com.diary.inn.InnDiary.work.domain.info.Member;
//import com.diary.inn.InnDiary.work.entity.info.DiaryEntity;
//import com.diary.inn.InnDiary.work.repository.api.DiaryJsonRepository;
//import com.diary.inn.InnDiary.work.repository.info.DiaryRepository;
//import com.diary.inn.InnDiary.work.repository.info.MemberRepository;
//import com.diary.inn.InnDiary.work.service.api.DiaryJsonService;
//import com.diary.inn.InnDiary.work.service.info.DiaryService;
//import com.diary.inn.InnDiary.work.service.info.MemberService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.stream.IntStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Slf4j
//class DiaryServiceTest {
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    DiaryJsonService diaryJsonService;
//    @Autowired
//    DiaryService diaryService;
//
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    DiaryJsonRepository diaryJsonRepository;
//    @Autowired
//    DiaryRepository diaryRepository;
//
//    public ObjectMapper objectMapper = new ObjectMapper();
//
//    public String JsonGenerator(int id) throws JsonProcessingException {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("seq", id);
//        map.put("title", "test" + id);
//        map.put("date", LocalDateTime.now().toString());
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
//            map.put("date", LocalDateTime.now().toString());
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
//    void memberAndDiaryJsonInsert() throws JsonProcessingException {
//        IntStream.rangeClosed(1, 3).forEach(i -> {
//            Member member = Member.builder()
//                    .loginId("test" + i + "@test.com")
//                    .company(0)
//                    .state(0)
//                    .build();
//
//            memberService.join(member);
//        });
//
//        Member member = Member.builder()
//                .loginId("test@test.com")
//                .company(0)
//                .state(0)
//                .build();
//
//        memberService.join(member);
//
//        IntStream.rangeClosed(1, 3).forEach(i -> {
//            Member find = memberService.findByEmailNCompany("test" + i + "@test.com", 0);
//            DiaryJson diarys = null;
//
//            try {
//                diarys = DiaryJson.builder()
//                    .memberSeq(find.getSeq())
//                    .memberState(find.getState())
//                    .memberCompany(find.getCompany())
//                    .memberId(find.getLoginId())
//                    .saveTitle("test" + i)
//                    .diary(LotsJsonGenerator(i))
//                    .build();
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//
//            diaryJsonService.join(diarys);
//        });
//
//        Member find = memberService.findByEmailNCompany("test@test.com", 0);
//
//        DiaryJson diary1 = DiaryJson.builder()
//                .memberSeq(find.getSeq())
//                .memberState(find.getState())
//                .memberCompany(find.getCompany())
//                .memberId(find.getLoginId())
//                .saveTitle("test 1")
//                .diary(JsonGenerator(5))
//                .build();
//
//        DiaryJson diaryA = DiaryJson.builder()
//                .memberSeq(find.getSeq())
//                .memberState(find.getState())
//                .memberCompany(find.getCompany())
//                .memberId(find.getLoginId())
//                .saveTitle("test A")
//                .diary(LotsJsonGenerator(6))
//                .build();
//
//        diaryJsonService.join(diary1);
//        diaryJsonService.join(diaryA);
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
//    void diaryJoin() {
//        // given
//        Member member = memberService.findByEmailNCompany("test@test.com", 0);
//        DiaryJson diaryJson = diaryJsonService.userDataFindWithSave(member, "test 1");
//
//        Diary diary = Diary.builder()
//                .title("test")
//                .content("test")
//                .status(0)
//                .weather(0)
//                .diaryId(10L)
//                .diaryDate(LocalDateTime.now())
//                .diarySaveDate(LocalDateTime.now())
//                .diaryJsonSeq(diaryJson.getSeq())
//                .build();
//
//        // when
//        diaryService.diaryJoin(diary);
//        List<DiaryEntity> found = diaryRepository.findAll();
//
//        // then
//        assertThat(found.get(0).getTitle()).isEqualTo(diary.getTitle());
//        assertThat(found.get(0).getDiary().getSeq()).isEqualTo(diary.getDiaryJsonSeq());
//    }
//
//    @Test
//    void diarySaveAll() {
//        // given
//        Member member = memberService.findByEmailNCompany("test@test.com", 0);
//        DiaryJson diaryJson1 = diaryJsonService.userDataFindWithSave(member, "test 1");
//        DiaryJson diaryJsonA = diaryJsonService.userDataFindWithSave(member, "test A");
//
//        // when
//        diaryService.diarySaveAll(diaryJson1);
//        List<DiaryEntity> found1 = diaryRepository.findAll();
//
//        diaryService.diarySaveAll(diaryJsonA);
//        List<DiaryEntity> foundA = diaryRepository.findAll();
//
//        // then
//        assertThat(found1.size()).isEqualTo(1);
//        assertThat(foundA.size()).isEqualTo(4);
//    }
//
//    @AfterEach
//    void deleteAllTestData() {
//        diaryRepository.deleteAll();
//        diaryJsonRepository.deleteAll();
//        memberRepository.deleteAll();
//    }
//}