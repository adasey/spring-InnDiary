package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

public interface DiaryConvertService {
    Diary entityToDto(DiaryEntity de);
    DiaryEntity dtoToEntity(Diary d);
}
