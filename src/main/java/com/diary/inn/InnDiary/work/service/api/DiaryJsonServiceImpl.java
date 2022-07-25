package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.api.DiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.api.ModifyDiaryJsonRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryJsonServiceImpl implements DiaryJsonService, DiaryJsonConversionService, DiaryJsonProcess {
    private final DiaryJsonRepository diaryJsonRepository;
    private final ModifyDiaryJsonRepository modifyDiaryJsonRepository;

    @Override
    public Long join(DiaryJson diaryJson) {
        return diaryJsonRepository.save(dtoToEntity(diaryJson)).getSeq();
    }

    @Override
    public void uploadTo(DiaryJson diaryJson) {
        modifyDiaryJsonRepository.updateJsonByEmailNSave(dtoToEntity(diaryJson));
    }

    @Override
    public void deleteWithEmailNSave(String email, String save) {
    }

    @Override
    public void updateLoadedData(DiaryJson diaryJson) {
    }

    @Override
    public List<DiaryJson> userDataFindAll(String email) {
        List<DiaryJsonEntity> found = modifyDiaryJsonRepository.findAllByEmail(email);
        List<DiaryJson> result = new ArrayList<>();

        for (DiaryJsonEntity entity : found) {
            result.add(entityToDto(entity));
        }
        return result;
    }

    @Override
    public DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity) {
        return DiaryJson.builder()
                .seq(diaryJsonEntity.getSeq())
                .loginId(diaryJsonEntity.getUser().getLoginId())
                .save(diaryJsonEntity.getSaveTitle())
                .Json(diaryJsonEntity.getDiary())
                .modDate(diaryJsonEntity.getModDate())
                .build();
    }

    @Override
    public DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity, MemberEntity memberEntity) {
        return DiaryJson.builder()
                .seq(diaryJsonEntity.getSeq())
                .loginId(memberEntity.getLoginId())
                .save(diaryJsonEntity.getSaveTitle())
                .Json(diaryJsonEntity.getDiary())
                .modDate(diaryJsonEntity.getModDate())
                .build();
    }

    @Override
    public DiaryJsonEntity dtoToEntity(DiaryJson diaryJson) {
        MemberEntity memberEntity = MemberEntity.builder()
                .loginId(diaryJson.getLoginId())
                .build();

        return DiaryJsonEntity.builder()
                .seq(diaryJson.getSeq())
                .user(memberEntity)
                .saveTitle(diaryJson.getSave())
                .diary(diaryJson.getJson())
                .build();
    }

    @Override
    public List<String> stringArrayToJson(List<JsonNode> diary) {
        return null;
    }

    @Override
    public List<JsonNode> jsonArrayToString(List<String> diary) {
        return null;
    }
}
