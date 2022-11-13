package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public interface CommonPerform {
    SlotEntity slotToEntity(Slot s);
    Slot slotEntityToDto(SlotEntity se);
    List<LocalDate> splitDate(LocalDate sDate, LocalDate eDate);
    List<LocalDate> insertSplitMonth(LocalDate ld, Period p);
    boolean isMonthMoreThenOne(Period p);
}
