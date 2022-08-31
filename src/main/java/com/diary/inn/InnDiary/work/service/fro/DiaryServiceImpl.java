package com.diary.inn.InnDiary.work.service.fro;

import com.diary.inn.InnDiary.work.domain.bef.DiaryJson;
import com.diary.inn.InnDiary.work.domain.fro.Diary;
import com.diary.inn.InnDiary.work.domain.fro.DiaryInfo;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.fro.DiaryEntity;
import com.diary.inn.InnDiary.work.repository.fro.DiaryRepository;
import com.diary.inn.InnDiary.work.repository.fro.SearchDiaryRepository;
import com.diary.inn.InnDiary.work.service.bef.DiaryJsonConversionService;
import com.diary.inn.InnDiary.work.service.bef.DiaryJsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryServiceImpl implements DiaryService, DiaryConversionService {
    private final DiaryRepository diaryRepository;
    private final DiaryJsonService diaryJsonService;
    private final DiaryJsonConversionService diaryJsonConversionService;
    private final SearchDiaryRepository searchDiaryRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Diary entityToDto(DiaryEntity entity, DiaryJsonEntity jsonEntity) {
        return Diary.builder()
                .seq(entity.getSeq())
                .diaryId(entity.getDiaryId())
                .title(entity.getTitle())
                .status(entity.getStatus())
                .weather(entity.getWeather())
                .content(entity.getContent())
                .diaryDate(entity.getDiaryDate())
                .diaryJsonSeq(jsonEntity.getSeq())
                .diarySlotNum(jsonEntity.getSlotNum())
                .diarySaveDate(jsonEntity.getModDate())
                .build();
    }

    @Override
    public DiaryEntity dtoToEntity(Diary dto) {
        DiaryJsonEntity diaryJsonEntity = DiaryJsonEntity.builder()
                .seq(dto.getDiaryJsonSeq())
                .build();

        return DiaryEntity.builder()
                .seq(dto.getSeq())
                .diaryId(dto.getDiaryId())
                .title(dto.getTitle())
                .status(dto.getStatus())
                .weather(dto.getWeather())
                .content(dto.getContent())
                .DiaryDate(dto.getDiaryDate())
                .diary(diaryJsonEntity)
                .build();
    }

    @Override
    public Diary diaryJoin(Diary diary) {
        DiaryEntity save = diaryRepository.save(dtoToEntity(diary));
//        DiaryJson diaryJson = diaryJsonService.savedDiaryFind(save.getDiaryId());

//        if (diaryJson != null) {
//            DiaryJsonEntity jsonEntity = diaryJsonConversionService.dtoToEntity(diaryJson);
//
//            return entityToDto(save, jsonEntity);
//        }
//        else {
        return null;
//        }
    }

    @Override
    public List<Diary> diarySaveAll(DiaryJson diaryJson) {
        List<DiaryInfo> buildJson = new ArrayList<>();
        List<Diary> result = new ArrayList<>();

        try {
            if (diaryJson.getDiaryList().contains("[") && diaryJson.getDiaryList().contains("]")) {
                buildJson = objectMapper.readValue(diaryJson.getDiaryList(), new TypeReference<>() {});
            }
            else {
                DiaryInfo diaryInfo = objectMapper.readValue(diaryJson.getDiaryList(), DiaryInfo.class);
                buildJson.add(diaryInfo);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("error reason search : {}", buildJson);

        for (DiaryInfo data : buildJson) {
            Diary diary = Diary.builder()
                    .diaryId(data.getSeq())
                    .diaryDate(LocalDateTime.parse(data.getDate()))
                    .content(data.getContent())
                    .weather(data.getWeather())
                    .status(data.getStatus())
                    .title(data.getTitle())
                    .diarySaveDate(diaryJson.getModDate())
                    .diarySlotNum(diaryJson.getSlotNum())
                    .diaryJsonSeq(diaryJson.getSeq())
                    .build();

            result.add(diary);

            diaryRepository.save(dtoToEntity(diary));
        }

        return result;
    }

    @Override
    public List<Diary> findDiaryByDiaryJson(DiaryJson diaryJson) {
        List<DiaryEntity> founds = searchDiaryRepository.diaryByDiaryJson(diaryJson.getSeq());
        List<Diary> results = new ArrayList<>();

//        if (founds != null) {
//            for (DiaryEntity diaryEntity : founds) {
//                Diary diary = entityToDto(diaryEntity, diaryJsonConversionService.dtoToEntity(diaryJsonService.savedDiaryFind(diaryEntity.getDiaryId())));
//
//                results.add(diary);
//            }
//
//            return results;
//        }

        return null;
    }

    @Override
    public void diaryDelete(List<DiaryJson> diaryJsons) {
        for (DiaryJson dj : diaryJsons) {
            searchDiaryRepository.deleteByDiaryJsonSeq(dj.getSeq());
        }
    }
}
