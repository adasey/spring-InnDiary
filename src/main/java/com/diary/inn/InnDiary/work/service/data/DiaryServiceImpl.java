package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.repository.DiaryRepository;
import com.diary.inn.InnDiary.work.repository.SearchDiaryRepository;
import com.diary.inn.InnDiary.work.service.CommonPerform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryServiceImpl extends CommonPerform implements DiaryService, DiaryConvertService {
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
    public void setWantSlot(Slot slot) {
        searchDiaryRepository.setSlot(slotToEntity(slot));
    }

    @Override
    public Slot getNowSlot() {
        return slotEntityToDto(searchDiaryRepository.getSlot());
    }

    @Override
    public boolean isSlotSetting() {
        return searchDiaryRepository.isSlotSetting();
    }

    @Override
    public Diary createDiary(Diary diary) {
        return entityToDto(diaryRepository.save(dtoToEntity(diary)));
    }

    @Override
    public Diary findDiaryBySeq(Long diarySeq) {
        if (searchDiaryRepository.isSlotSetting()) {
            return entityToDto(searchDiaryRepository.findBySeq(diarySeq));
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Diary> findAllDiaryBySlot() {
        if (searchDiaryRepository.isSlotSetting()) {
            List<Diary> found = new ArrayList<>();
            List<DiaryEntity> sfAll = searchDiaryRepository.findAllBySlot();

            for (DiaryEntity de : sfAll) {
                found.add(entityToDto(de));
            }
            return found;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Diary> findMonthDiary(LocalDate date) {
        if (searchDiaryRepository.isSlotSetting()) {
            List<Diary> result = new ArrayList<>();
            List<DiaryEntity> diaryByDate = searchDiaryRepository.findByMonthDate(date);

            for (DiaryEntity de : diaryByDate) {
                result.add(entityToDto(de));
            }
            return result;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Diary> findSixMonthDiary(LocalDate date) {
        if (searchDiaryRepository.isSlotSetting()) {
            List<Diary> rDiary = new ArrayList<>();

            for (int i = -2; i <= 3; i++) {
                LocalDate dDate = null;
                if (i <= 0) {
                    dDate = date.minusMonths(-i);
                } else {
                    dDate = date.plusMonths(i);
                }

                List<DiaryEntity> findMonthDate = searchDiaryRepository.findByMonthDate(dDate);

                for (DiaryEntity de : findMonthDate) {
                    rDiary.add(entityToDto(de));
                }
            }

            return rDiary;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Diary> findBetweenMonthDiary(LocalDate startDate, LocalDate endDate) {
        if (searchDiaryRepository.isSlotSetting()) {
            List<LocalDate> dates = splitDate(startDate, endDate);
            List<Diary> result = new ArrayList<>();

            for (int i = 0; i < dates.size(); i += 2) {
                List<DiaryEntity> betweenMonthDiary = searchDiaryRepository.findByBetweenMonthDate(dates.get(i), dates.get(i + 1));

                for (DiaryEntity de : betweenMonthDiary) {
                    result.add(entityToDto(de));
                }
            }

            return result;
        }

        log.info("please setting slot value before find");
        return null;
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
