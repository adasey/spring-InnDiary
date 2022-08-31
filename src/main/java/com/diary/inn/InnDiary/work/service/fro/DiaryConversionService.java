package com.diary.inn.InnDiary.work.service.fro;

import com.diary.inn.InnDiary.work.domain.fro.Diary;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.fro.DiaryEntity;

public interface DiaryConversionService {
    Diary entityToDto(DiaryEntity entity, DiaryJsonEntity jsonEntity);
    DiaryEntity dtoToEntity(Diary dto);
}
