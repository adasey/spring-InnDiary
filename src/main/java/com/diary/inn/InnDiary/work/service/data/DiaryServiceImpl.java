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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
