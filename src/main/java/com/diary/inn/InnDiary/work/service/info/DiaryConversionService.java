package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.info.Diary;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.DiaryEntity;

public interface DiaryConversionService {
    Diary entityToDto(DiaryEntity entity, DiaryJsonEntity jsonEntity);
    DiaryEntity dtoToEntity(Diary dto);
}
