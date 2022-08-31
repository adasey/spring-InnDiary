package com.diary.inn.InnDiary.work.service.bef;

import com.diary.inn.InnDiary.work.domain.bef.DiaryJson;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;

public interface DiaryJsonConversionService {
    DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity);
    DiaryJsonEntity dtoToEntity(DiaryJson diaryJson);
}
