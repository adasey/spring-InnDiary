package com.diary.inn.InnDiary.diaryjson;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.api.DiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.api.ModifyDiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.info.MemberRepository;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonConversionService;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonService;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class DiaryJsonServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberConversionService memberConversionService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiaryJsonService diaryJsonService;
    @Autowired
    DiaryJsonConversionService diaryJsonConversionService;
    @Autowired
    DiaryJsonRepository diaryJsonRepository;
    @Autowired
    ModifyDiaryJsonRepository modifyDiaryJsonRepository;

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

    @AfterEach
    void deleteAll() {
        diaryJsonRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void entityToDtoDiaryJsonOnlyTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");

        DiaryJsonEntity entity = DiaryJsonEntity.builder()
                .diary(JsonGenerator(1))
                .saveTitle("test")
                .build();

        // when
        DiaryJson result = diaryJsonConversionService.entityToDto(entity);

        // then
        assertThat(entity.getDiary()).isEqualTo(result.getDiary());
        assertThat(entity.getSaveTitle()).isEqualTo(result.getSaveTitle());
    }

    @Test
    void entityToDtoTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");
        MemberEntity memberEntity = memberConversionService.dtoToEntity(find);

        DiaryJsonEntity entity = DiaryJsonEntity.builder()
                .member(memberEntity)
                .diary(JsonGenerator(1))
                .saveTitle("test")
                .build();

        // when
        DiaryJson result = diaryJsonConversionService.entityToDto(entity, memberEntity);

        // then
        assertThat(entity.getMember().getSeq()).isEqualTo(result.getMemberSeq());
    }

    @Test
    void dtoToEntityTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");
        MemberEntity memberEntity = memberConversionService.dtoToEntity(find);

        DiaryJson dto = DiaryJson.builder()
                .memberSeq(find.getSeq())
                .diary(JsonGenerator(1))
                .saveTitle("test")
                .build();

        // when
        DiaryJsonEntity result = diaryJsonConversionService.dtoToEntity(dto);

        // then
        assertThat(dto.getMemberSeq()).isEqualTo(result.getMember().getSeq());
        assertThat(dto.getDiary()).isEqualTo(result.getDiary());
    }

    @Test
    @Transactional
    void joinTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");

        DiaryJson dto = DiaryJson.builder()
                .memberSeq(find.getSeq())
                .memberState(find.getState())
                .memberCompany(find.getCompany())
                .memberId(find.getLoginId())
                .saveTitle("test")
                .diary(LotsJsonGenerator(1))
                .build();

        log.info("diary saved member seq : {}", dto.getMemberSeq());

        // when
        Long findSeq = diaryJsonService.join(dto);
        Optional<DiaryJsonEntity> diaryJson = diaryJsonRepository.findById(dto.getMemberSeq());
        if (diaryJson.isPresent()) {
            log.info("diary saved test : {}", diaryJson.get().getSaveTitle());
            log.info("diary saved test : {}", diaryJson.get().getDiary());
            log.info("diary saved test : {}", diaryJson.get().getMember().getSeq());
        }

        // then
        diaryJson.ifPresent(entity -> assertThat(findSeq).isEqualTo(entity.getSeq()));
    }

    @Test
//    @Transactional if use transactional it's not work, but if delete it works fine
    void uploadToJsonTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");
        MemberEntity en = memberConversionService.dtoToEntity(find);
        DiaryJson dto = null;
        for (int i = 1; i <= 3; i++) {
            Member member = memberService.findByEmail("test" + i);

            dto = DiaryJson.builder()
                .memberSeq(member.getSeq())
                .memberState(member.getState())
                .memberCompany(member.getCompany())
                .memberId(member.getLoginId())
                .saveTitle("test" + i)
                .diary(LotsJsonGenerator(i))
                .build();

            diaryJsonService.join(dto);
        }
        DiaryJsonEntity found = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(en, "test1");

        // when
        String json = JsonGenerator(2);
        log.info("found value check : {}", found);
        diaryJsonService.uploadToJson(found.getSeq(), json);

        List<DiaryJsonEntity> all = diaryJsonRepository.findAll();
        DiaryJsonEntity result = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(en, "test1");
        log.info("result value check : {}", result);
        log.info("maybe value check : {}", all);

        // then
        assertThat(json).isEqualTo(result.getDiary());
    }

    @Test
    void uploadToSaveTitleTest() throws JsonProcessingException {
        // given
        DiaryJson dto = null;
        for (int i = 1; i <= 3; i++) {
            Member member = memberService.findByEmail("test" + i);

            dto = DiaryJson.builder()
                    .memberSeq(member.getSeq())
                    .memberState(member.getState())
                    .memberCompany(member.getCompany())
                    .memberId(member.getLoginId())
                    .saveTitle("test" + i)
                    .diary(LotsJsonGenerator(i))
                    .build();

            diaryJsonService.join(dto);
        }

        Member find = memberService.findByEmail("test1");
        MemberEntity entity = memberConversionService.dtoToEntity(find);
        DiaryJsonEntity found = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(entity, "test1");

        // when
        String saveTitle = "test of test";
        diaryJsonService.uploadToSaveTitle(found.getSeq(), saveTitle);
        DiaryJsonEntity result = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(entity, saveTitle);

        // then
        assertThat(saveTitle).isEqualTo(result.getSaveTitle());
    }

    @Test
    void deleteWithEmailNSaveTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");

        DiaryJson dto = DiaryJson.builder()
                .memberSeq(find.getSeq())
                .memberState(find.getState())
                .memberCompany(find.getCompany())
                .memberId(find.getLoginId())
                .saveTitle("test")
                .diary(LotsJsonGenerator(1))
                .build();

        Long findSeq = diaryJsonService.join(dto);

        // when
        diaryJsonService.deleteWithEmailNSave(find, "test");

        // then
        assertThat(diaryJsonService.userDataFindWithSave(find, "test")).isNull();
    }

    @Transactional
    @Test
    void userDataFindAllTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");
        DiaryJson dto = null;
        for (int i = 1; i <= 3; i++) {

            dto = DiaryJson.builder()
                    .memberSeq(find.getSeq())
                    .memberState(find.getState())
                    .memberCompany(find.getCompany())
                    .memberId(find.getLoginId())
                    .saveTitle("test" + i)
                    .diary(LotsJsonGenerator(i))
                    .build();

            diaryJsonService.join(dto);
        }

        // when
        List<DiaryJson> results = diaryJsonService.userDataFindAll(find);

        // then
        assertThat(dto.getMemberId()).isEqualTo(results.get(results.size() - 1).getMemberId());
        assertThat(dto.getDiary()).isEqualTo(results.get(results.size() - 1).getDiary());
        assertThat(dto.getSaveTitle()).isEqualTo(results.get(results.size() - 1).getSaveTitle());
    }

    @Transactional
    @Test
    void userDataFindTest() throws JsonProcessingException {
        // given
        Member find = memberService.findByEmail("test1");

        DiaryJson dto = DiaryJson.builder()
                .memberSeq(find.getSeq())
                .memberState(find.getState())
                .memberCompany(find.getCompany())
                .memberId(find.getLoginId())
                .saveTitle("test")
                .diary(LotsJsonGenerator(1))
                .build();

        Long findSeq = diaryJsonService.join(dto);

        // when
        DiaryJson result = diaryJsonService.userDataFindWithSave(find, "test");

        // then
        assertThat(result.getSaveTitle()).isEqualTo("test");
        assertThat(result.getMemberId()).isEqualTo(find.getLoginId());
    }
}