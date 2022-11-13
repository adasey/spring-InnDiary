package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.diary.Diary;
import com.diary.inn.InnDiary.entity.diary.DiaryEntity;

public interface DiaryConvertService {
    Diary entityToDto(DiaryEntity de);
    DiaryEntity dtoToEntity(Diary d);
}
