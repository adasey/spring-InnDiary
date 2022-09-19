package com.diary.inn.InnDiary.work.service;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class CommonPerform {
    public SlotEntity slotToEntity(Slot s) {
        UserEntity u = new UserEntity(s.getSeq(), s.getUserName(), s.getUserEmail(), s.getUserUid());

        return SlotEntity.builder()
                .seq(s.getSeq())
                .user(u)
                .which(s.getWhich())
                .slotNum(s.getSlotNum())
                .title(s.getTitle())
                .modDate(s.getModDate())
                .build();
    }

    public Slot slotEntityToDto(SlotEntity se) {
        return Slot.builder()
                .seq(se.getSeq())
                .title(se.getTitle())
                .slotNum(se.getSlotNum())
                .modDate(se.getModDate())
                .userSeq(se.getUser().getId())
                .userEmail(se.getUser().getEmail())
                .userName(se.getUser().getName())
                .userUid(se.getUser().getUid())
                .build();
    }

    protected List<LocalDate> splitDate(LocalDate sDate, LocalDate eDate) {
        List<LocalDate> result = new ArrayList<>();
        LocalDate localDate = LocalDate.of(sDate.getYear(), sDate.getMonth(), 1);
        Period period = Period.between(sDate, eDate);

        result.add(sDate);
        result.add(LocalDate.of(sDate.getYear(), sDate.getMonth(), sDate.lengthOfMonth()));

        result.addAll(insertSplitMonth(localDate, period));

        result.add(LocalDate.of(eDate.getYear(), eDate.getMonth(), 1));
        result.add(eDate);

        return result;
    }

    private List<LocalDate> insertSplitMonth(LocalDate ld, Period p) {
        List<LocalDate> r = new ArrayList<>();

        if (isMonthMoreThenOne(p)) {
            for (int i = 1; i < p.getMonths(); i++) {
                ld = ld.plusMonths(1);
                r.add(ld);
                r.add(LocalDate.of(ld.getYear(), ld.getMonth(), ld.lengthOfMonth()));
            }
        }

        return r;
    }

    private boolean isMonthMoreThenOne(Period p) {
        return p.getMonths() > 0;
    }
}
