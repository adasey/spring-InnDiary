package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;

public interface DiaryJsonConversionService {
    DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity);
    DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity, MemberEntity memberEntity);
    DiaryJsonEntity dtoToEntity(DiaryJson diaryJson);
}
