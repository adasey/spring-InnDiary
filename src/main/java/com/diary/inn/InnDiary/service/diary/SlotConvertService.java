package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;

public interface SlotConvertService {
    Slot entityToDto(SlotEntity se);
    SlotEntity dtoToEntity(Slot slot);
}
