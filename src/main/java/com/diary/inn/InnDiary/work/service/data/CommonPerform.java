package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

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
