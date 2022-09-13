package com.diary.inn.InnDiary.work.service.json;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.DiaryMeta;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.SlotMeta;
import com.diary.inn.InnDiary.work.repository.firebase.FirebaseJsonRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Qualifier("DiaryFirebaseJsonServiceImpl")
@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryFirebaseJsonServiceImpl implements DiaryFirebaseJsonService {
    private final FirebaseJsonRepository firebaseJsonRepository;
    private Map<Integer, List<DiaryMeta>> diaryDataAll = new HashMap<>();

    @Override
    public List<Slot> processAllSlotFromFirebaseData(User u) {
        List<Slot> rs = new ArrayList<>();

        for (Integer i :isAnySlotHasData()) {
            SlotMeta s = settingSlotMeta(firebaseJsonRepository.getWhichSlotNumData("diarySlot", "slot" + i), i);

            rs.add(Slot.builder()
                    .slotNum(i)
                    .userSeq(u.getId())
                    .userName(u.getName())
                    .userEmail(u.getEmail())
                    .userUid(u.getUid())
                    .title(s.getTitle())
                    .which("diary")
                    .modDate(stringToLocalDate(s.getModDate()))
                    .build());
        }
        return rs;
    }

    private SlotMeta settingSlotMeta(DataSnapshot ds, int i) {
        GenericTypeIndicator<List<DiaryMeta>> tD = new GenericTypeIndicator<List<DiaryMeta>>() {};

        SlotMeta s = ds.getValue(SlotMeta.class);
        List<DiaryMeta> dList = ds.child("diaryList").getValue(tD);

        diaryDataAll.put(i, dList);

        return s;
    }

    private LocalDate stringToLocalDate(String time) {
        LocalDate ldt = null;

        if (time.length() > 8) {
            ldt = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }
        else {
            ldt = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return ldt;
    }

    @Override
    public Map<Integer, List<DiaryMeta>> getDiaryWithSlot() {
        return this.diaryDataAll;
    }

    @Override
    public List<Integer> isAnySlotHasData() {
        List<Integer> isSlotExists = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            if (firebaseJsonRepository.getWhichData("diarySlot").hasChild("slot" + i)) {
                isSlotExists.add(i);
            }
        }
        return isSlotExists;
    }
}
