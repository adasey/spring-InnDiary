package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.entity.UserEntity;
import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import com.diary.inn.InnDiary.repository.diary.ModifySlotRepository;
import com.diary.inn.InnDiary.repository.diary.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Qualifier("SlotServiceImpl")
@Service
@RequiredArgsConstructor
@Slf4j
public class SlotServiceImpl implements SlotService, SlotConvertService {
    private final SlotRepository slotRepository;
    private final ModifySlotRepository modifySlotRepository;

    @Override
    public Slot entityToDto(SlotEntity se) {
        return Slot.builder()
                .seq(se.getSeq())
                .title(se.getTitle())
                .which(se.getWhich())
                .slotNum(se.getSlotNum())
                .userSeq(se.getUser().getId())
                .userName(se.getUser().getName())
                .userEmail(se.getUser().getEmail())
                .userUid(se.getUser().getUid())
                .modDate(se.getModDate())
                .build();
    }

    @Override
    public SlotEntity dtoToEntity(Slot s) {
        UserEntity u = new UserEntity(s.getUserSeq(), s.getUserName(), s.getUserEmail(), s.getUserUid());

        return SlotEntity.builder()
                .seq(s.getSeq())
                .title(s.getTitle())
                .which(s.getWhich())
                .slotNum(s.getSlotNum())
                .modDate(s.getModDate())
                .user(u)
                .build();
    }

    @Override
    public Slot createSlot(Slot s) {
        return entityToDto(slotRepository.save(dtoToEntity(s)));
    }

    @Override
    public Slot findWhichSlotByNum(User user, String which, Integer num) {
        return entityToDto(modifySlotRepository.findWhichSlotByUserNNum(user, which, num));
    }

    @Override
    public List<Slot> findWhichSlot(User user, String which) {
        return searchSlotAll(modifySlotRepository.findWhichSlotByUser(user, which));
    }

    @Override
    public List<Slot> findWhichSlotByUid(String uid, String which) {
        return searchSlotAll(modifySlotRepository.findWhichSlotByUid(uid, which));
    }

    @Override
    public List<Slot> findAllSlotByUser(User user) {
        return searchSlotAll(modifySlotRepository.findAllByUid(user.getUid()));
    }

    private List<Slot> searchSlotAll(List<SlotEntity> ses) {
        List<Slot> ss = new ArrayList<>();

        for (SlotEntity se : ses) {
            Slot s = entityToDto(se);

            ss.add(s);
        }

        return ss;
    }

    @Override
    public void updateBySlotNum(Slot s) {
        modifySlotRepository.updateSlotBySlotNum(dtoToEntity(s));
    }

    @Override
    public void deleteSlot(Slot slot) {
        modifySlotRepository.deleteBySlot(dtoToEntity(slot));
    }
}
