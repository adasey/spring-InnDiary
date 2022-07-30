package com.diary.inn.InnDiary.diaryjson;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.repository.api.DiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.api.ModifyDiaryJsonRepository;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.concurrent.atomic.AtomicReference;
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
        IntStream.rangeClosed(1, 3).forEach(i -> {
            Member member = Member.builder()
                    .loginId("test" + i)
                    .company(0)
                    .state(0)
                    .build();
            memberService.join(member);
        });
    }

    @Test
    @Transactional
    void diaryRepoFindAllSingleSingleJsonTest() throws JsonProcessingException {
        // given
        AtomicReference<Member> member = new AtomicReference<>(); // 공부할 것.

        List<DiaryJsonEntity> result = new ArrayList<>();
        IntStream.rangeClosed(1, 3).forEach(i -> {
            member.set(memberService.findByEmail("test" + i));

            DiaryJsonEntity entity = null;
            try {
                entity = DiaryJsonEntity.builder()
                        .member(memberConversionService.dtoToEntity(member.get()))
                        .saveTitle("test" + i)
                        .diary(JsonGenerator(i))
                        .build();
                 result.add(diaryJsonRepository.save(entity));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        // when
        List<DiaryJsonEntity> test = modifyDiaryJsonRepository.findAllByEmailNCompany(memberConversionService.dtoToEntity(member.get()));

        // then
        log.info("test of saving : {} {}", member, result.get(0).getMember());
        log.info("check what's inside : {}", test.get(0));
        assertThat(test.get(0)).isEqualTo(result.get(2));
        assertThat(test.get(0).getDiary()).isEqualTo(JsonGenerator(3));
    }

    @Test
    @Transactional
    void diaryRepoFindAllSingleBunchJsonTest() throws JsonProcessingException {
        // works fine. but if i use service, it didn't work
        // given
        Member member = memberService.findByEmail("test1");

        DiaryJsonEntity entity = DiaryJsonEntity.builder()
                .member(memberConversionService.dtoToEntity(member))
                .saveTitle("test")
                .diary(LotsJsonGenerator(1))
                .build();

        DiaryJsonEntity result = diaryJsonRepository.save(entity);

        // when
        List<DiaryJsonEntity> test = modifyDiaryJsonRepository.findAllByEmailNCompany(memberConversionService.dtoToEntity(member));

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
        Member member = memberService.findByEmail("test1");

        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;
            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .member(memberConversionService.dtoToEntity(member))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            results.add(diaryJsonRepository.save(entity));
        });

        // when
        List<DiaryJsonEntity> test = modifyDiaryJsonRepository.findAllByEmailNCompany(memberConversionService.dtoToEntity(member));

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
        Member member = memberService.findByEmail("test1");

        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;

            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .member(memberConversionService.dtoToEntity(member))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            log.info("what value is in : {}", ("test" + i)); // "test1", "test2", "test3" insert

            results.add(diaryJsonRepository.save(entity));
        });

        // when
        DiaryJsonEntity test = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), "test1");

        // then
        log.info("result test : {}", test);

        assertThat(results.get(0)).isEqualTo(test);
    }

    @Test
    @Transactional
    void diaryRepoSaveJsonByEmailNSaveTest() throws JsonProcessingException {
        // given
        Member member = memberService.findByEmail("test1");

        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;

            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .member(memberConversionService.dtoToEntity(member))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            results.add(entity);
            diaryJsonRepository.save(entity);
        });

//        JsonNode js = objectMapper.readTree(JsonGenerator(2));
//        JsonNode js = objectMapper.readTree(LotsJsonGenerator(1));

        DiaryJsonEntity diaryJson = DiaryJsonEntity.builder()
                .member(memberConversionService.dtoToEntity(member))
                .saveTitle("test1")
                .diary(JsonGenerator(2))
                .build();

        DiaryJsonEntity test = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), "test1");
        log.info("result test : {}", test);

        test.setDiary(JsonGenerator(2));

        // when
        modifyDiaryJsonRepository.updateJson(test.getSeq(), diaryJson.getDiary());
        DiaryJsonEntity found = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), "test1");

        // then
        log.info("founded value : {}", found);

        assertThat(results.get(0)).isEqualTo(test);
        assertThat(found.getMember().getLoginId()).isEqualTo(diaryJson.getMember().getLoginId());
        assertThat(found.getDiary()).isEqualTo(diaryJson.getDiary());
        assertThat(test.getDiary()).isEqualTo(found.getDiary());
    }

    @Test
    @Transactional
    void diaryRepoSaveSaveTitleByEmailNSaveTest() throws JsonProcessingException {
        // given
        Member member = memberService.findByEmail("test1");

        List<DiaryJsonEntity> results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            String json = null;

            try {
                json = LotsJsonGenerator(i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            DiaryJsonEntity entity = DiaryJsonEntity.builder()
                    .member(memberConversionService.dtoToEntity(member))
                    .saveTitle("test" + i)
                    .diary(json)
                    .build();

            results.add(diaryJsonRepository.save(entity));
        });

//        JsonNode js = objectMapper.readTree(JsonGenerator(2));
//        JsonNode js = objectMapper.readTree(LotsJsonGenerator(1));

        DiaryJsonEntity diaryJson = DiaryJsonEntity.builder()
                .member(memberConversionService.dtoToEntity(member))
                .saveTitle("test test")
                .diary(JsonGenerator(2))
                .build();

        DiaryJsonEntity test = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), "test1");
        log.info("result test : {}", test);

        test.setSaveTitle("test test");

        // when
        modifyDiaryJsonRepository.updateSaveTitle(test.getSeq(), test.getSaveTitle());
        DiaryJsonEntity found = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), "test test");

        // then
        log.info("founded value : {}", found);

        assertThat(results.get(0)).isEqualTo(test);
        assertThat(found.getMember().getLoginId()).isEqualTo(diaryJson.getMember().getLoginId());
        assertThat(found.getSaveTitle()).isEqualTo(diaryJson.getSaveTitle());
    }
}
