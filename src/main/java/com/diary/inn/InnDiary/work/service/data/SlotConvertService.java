package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

public interface SlotConvertService {
    Slot entityToDto(SlotEntity se);
    SlotEntity dtoToEntity(Slot slot);
}
