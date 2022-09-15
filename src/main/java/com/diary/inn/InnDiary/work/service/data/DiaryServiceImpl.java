package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.repository.DiaryRepository;
import com.diary.inn.InnDiary.work.repository.SearchDiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryServiceImpl implements DiaryService, DiaryConversionService {
    private final DiaryRepository diaryRepository;
    private final SearchDiaryRepository searchDiaryRepository;

    @Override
    public Diary entityToDto(DiaryEntity de) {
        return Diary.builder()
                .dSeq(de.getDSeq())
                .diarySeq(de.getDiarySeq())
                .title(de.getTitle())
                .status(de.getStatus())
                .weather(de.getWeather())
                .content(de.getContent())
                .date(de.getDate())
                .slotSeq(de.getSlot().getSeq())
                .build();
    }

    @Override
    public DiaryEntity dtoToEntity(Diary d) {
        SlotEntity se = SlotEntity.builder()
                .seq(d.getSlotSeq())
                .build();

        return DiaryEntity.builder()
                .dSeq(d.getDSeq())
                .diarySeq(d.getDiarySeq())
                .title(d.getTitle())
                .status(d.getStatus())
                .weather(d.getWeather())
                .date(d.getDate())
                .content(d.getContent())
                .slot(se)
                .build();
    }

    @Override
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

    @Override
    public Diary createDiary(Diary diary) {
        return entityToDto(diaryRepository.save(dtoToEntity(diary)));
    }

    @Override
    public Diary findDiaryBySlotNSeq(Slot slot, Long diarySeq) {
        return entityToDto(searchDiaryRepository.findBySlotNSeq(slotToEntity(slot), diarySeq));
    }

    @Override
    public List<Diary> findAllDiaryBySlot(Slot slot) {
        List<Diary> found = new ArrayList<>();
        List<DiaryEntity> sfAll = searchDiaryRepository.findAllBySlot(slotToEntity(slot));

        for (DiaryEntity de : sfAll) {
            found.add(entityToDto(de));
        }
        return found;
    }

    @Override
    public List<Diary> findMonthDiary(LocalDate date) {
        List<Diary> result = new ArrayList<>();
        List<DiaryEntity> diaryByDate = searchDiaryRepository.findByMonthDate(date);

        for (DiaryEntity de : diaryByDate) {
            result.add(entityToDto(de));
        }
        return result;
    }

    @Override
    public Map<LocalDate, List<Diary>> findSixMonthDiary(LocalDate date) {
        Map<LocalDate, List<Diary>> result = new HashMap<>();
        List<Diary> rDiary = new ArrayList<>();

        for (int i = -2; i <= 3; i++) {
            LocalDate dDate = null;
            if (i <= 0) {
                 dDate = date.minusMonths(-i);
            }
            else {
                dDate = date.plusMonths(i);
            }

            List<DiaryEntity> findMonthDate = searchDiaryRepository.findByMonthDate(dDate);

            for (DiaryEntity de : findMonthDate) {
                rDiary.add(entityToDto(de));
            }

            result.put(dDate, rDiary);
            rDiary.clear();
        }

        return result;
    }

    @Override
    public Map<LocalDate, List<Diary>> findBetweenMonthDiary(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = splitDate(startDate, endDate);
        Map<LocalDate, List<Diary>> result = new HashMap<>();
        List<Diary> rd = new ArrayList<>();

        for (int i = 0; i < dates.size(); i += 2) {
            List<DiaryEntity> betweenMonthDiary = searchDiaryRepository.findByBetweenMonthDate(dates.get(i), dates.get(i + 1));

            for (DiaryEntity de : betweenMonthDiary) {
                rd.add(entityToDto(de));
            }

            result.put(dates.get(i), rd);
            rd.clear();
        }

        return result;
    }

    private List<LocalDate> splitDate(LocalDate sDate, LocalDate eDate) {
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

    @Override
    public void updateDiary(Diary diary) {
        searchDiaryRepository.updateDiary(dtoToEntity(diary));
    }

    @Override
    public void deleteDiary(Diary diary) {
        diaryRepository.delete(dtoToEntity(diary));
    }

    @Override
    public void deleteDiaryBySlot(Long slotSeq) {
        searchDiaryRepository.deleteBySlot(slotSeq);
    }
}
