package com.diary.inn.InnDiary.diaryjson;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.repository.api.DiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.api.ModifyDiaryJsonRepository;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class DiaryJsonRepoTest {
    public ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    ModifyDiaryJsonRepository modifyDiaryJsonRepository;
    @Autowired
    DiaryJsonRepository diaryJsonRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberConversionService memberConversionService;

//    public String JsonGenerator(int id) {
//        return "{ " +
//                "\"title\" : \"test" + id + "\", " +
//                "\"date\" : \"20220707" + id + "\", " +
//                "\"weather\" : 0, " +
//                "\"status\" : 0, " +
//                "\"content\" : \"test for" + id + "\"" +
//                "}";
//    }

//    public String LotsJsonGenerator(int id) {
//        return "[{ " +
//                "\"title\" : \"test" + id + "\", " +
//                "\"date\" : \"20220707" + id + "\", " +
//                "\"weather\" : 0, " +
//                "\"status\" : 0, " +
//                "\"content\" : \"test for" + id + "\"" +
//                "}, " +
//                "{ " +
//                "\"title\" : \"test" + id + " " + id + "\", " +
//                "\"date\" : \"20220707" + id + " " + id + "\", " +
//                "\"weather\" : 0, " +
//                "\"status\" : 0, " +
//                "\"content\" : \"test for" + id + " " + id + "\"" +
//                "}," +
//                "{ " +
//                "\"title\" : \"test" + id + " " + id + " " + id + "\", " +
//                "\"date\" : \"20220707" + id + " " + id + " " + id + "\", " +
//                "\"weather\" : 0, " +
//                "\"status\" : 0, " +
//                "\"content\" : \"test for" + id + " " + id + " " + id + "\"" +
//                "}]";
//    }

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

    @Test
    @Transactional
    void diaryRepoFindAllSingleSingleJsonTest() throws JsonProcessingException {
        // given
        DiaryJsonEntity entity = DiaryJsonEntity.builder()
                .user(memberConversionService.dtoToEntity(memberService.findByEmail("test")))
                .saveTitle("test")
                .diary(JsonGenerator(1))
                .build();

        DiaryJsonEntity result = diaryJsonRepository.save(entity);

        // when
        List<DiaryJsonEntity> test = modifyDiaryJsonRepository.findAllByEmail("test");

        // then
        log.info("test of saving : {} {}", entity, result);
        log.info("check what's inside : {}", test.get(0));
        assertThat(test.get(0)).isEqualTo(result);
        assertThat(test.get(0).getDiary()).isEqualTo(JsonGenerator(1));
    }

    @Test
    @Transactional
    void diaryRepoFindAllSingleBunchJsonTest() throws JsonProcessingException {
        // given
        DiaryJsonEntity entity = DiaryJsonEntity.builder()
                .user(memberConversionService.dtoToEntity(memberService.findByEmail("test")))
                .saveTitle("test")
                .diary(LotsJsonGenerator(1))
                .build();

        DiaryJsonEntity result = diaryJsonRepository.save(entity);

        // when
        List<DiaryJsonEntity> test = modifyDiaryJsonRepository.findAllByEmail("test");

        // then
        log.info("test of saving : {} {}", entity, result);
        log.info("check what's inside : {}", test.get(0));
        assertThat(test.get(0)).isEqualTo(result);
        assertThat(test.get(0).getDiary()).isEqualTo(LotsJsonGenerator(1));
    }

    @Test
    @Transactional
    void diaryRepoFindAllBunchJsonTest() {
        // given
        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;
            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .user(memberConversionService.dtoToEntity(memberService.findByEmail("test")))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            results.add(diaryJsonRepository.save(entity));
        });

        // when
        List<DiaryJsonEntity> test = modifyDiaryJsonRepository.findAllByEmail("test");

        // then
        for (int i = 1; i <= 3; i++) {
            assertThat(test.get(i - 1)).isEqualTo(results.get(i - 1));

            String json = null;

            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            log.info("result test of " + (i - 1) + " : {}", test.get(i - 1));

            assertThat(test.get(i - 1).getDiary()).isEqualTo(json);
        }
    }

    @Test
    @Transactional
    void diaryRepoFindBySaveTest() {
        // given
        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;

            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .user(memberConversionService.dtoToEntity(memberService.findByEmail("test")))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            log.info("what value is in : {}", ("test" + i)); // "test1", "test2", "test3" insert

            results.add(diaryJsonRepository.save(entity));
        });

        // when
        DiaryJsonEntity test = modifyDiaryJsonRepository.findByEmailNSave("test", "test1");

        // then
        log.info("result test : {}", test);

        assertThat(results.get(0)).isEqualTo(test);
    }

    @Test
    @Transactional
    void diaryRepoSaveJsonByEmailNSaveTest() throws JsonProcessingException {
        // given
        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;

            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .user(memberConversionService.dtoToEntity(memberService.findByEmail("test")))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            results.add(diaryJsonRepository.save(entity));
        });

//        JsonNode js = objectMapper.readTree(JsonGenerator(2));
//        JsonNode js = objectMapper.readTree(LotsJsonGenerator(1));

        DiaryJsonEntity diaryJson = DiaryJsonEntity.builder()
                .user(memberConversionService.dtoToEntity(memberService.findByEmail("test")))
                .saveTitle("test1")
                .diary(JsonGenerator(2))
                .build();

        DiaryJsonEntity test = modifyDiaryJsonRepository.findByEmailNSave("test", "test1");
        log.info("result test : {}", test);

        test.setDiary(JsonGenerator(2));

        // when
        modifyDiaryJsonRepository.saveJsonByEmailNSave(test);
        DiaryJsonEntity found = modifyDiaryJsonRepository.findByEmailNSave("test", "test1");

        // then
        log.info("founded value : {}", found);

        assertThat(results.get(0)).isEqualTo(test);
        assertThat(found.getUser().getLoginId()).isEqualTo(diaryJson.getUser().getLoginId());
        assertThat(found.getDiary()).isEqualTo(diaryJson.getDiary());
    }
}
