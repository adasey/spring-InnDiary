package com.diary.inn.InnDiary.work.service.bef;

import com.diary.inn.InnDiary.work.domain.bef.DiaryJson;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.repository.bef.DiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.bef.ModifyDiaryJsonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DiaryJsonServiceImpl implements DiaryJsonService, DiaryJsonConversionService {
    private final DiaryJsonRepository diaryJsonRepository;
    private final ModifyDiaryJsonRepository modifyDiaryJsonRepository;

    @Override
    public DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity) {
        return DiaryJson.builder()
                .seq(diaryJsonEntity.getSeq())
                .diarySlot(diaryJsonEntity.getDiarySlot())
                .slotNum(diaryJsonEntity.getSlotNum())
                .modDate(diaryJsonEntity.getModDate())
                .diaryList(diaryJsonEntity.getDiaryList())
                .build();
    }

    @Override
    public DiaryJsonEntity dtoToEntity(DiaryJson diaryJson) {
        return DiaryJsonEntity.builder()
                .seq(diaryJson.getSeq())
                .diarySlot(diaryJson.getDiarySlot())
                .slotNum(diaryJson.getSlotNum())
                .modDate(diaryJson.getModDate())
                .diaryList(diaryJson.getDiaryList())
                .build();
    }

    @Override
    public Long join(DiaryJson diaryJson) {
        DiaryJsonEntity result = diaryJsonRepository.save(dtoToEntity(diaryJson));
        return result.getSeq();
    }

    @Override
    public void deleteDiarySlot(String diarySlot) {

    }

    @Override
    public void deleteDiarySlotByNum(String diarySlot, int slotNum) {

    }
}
