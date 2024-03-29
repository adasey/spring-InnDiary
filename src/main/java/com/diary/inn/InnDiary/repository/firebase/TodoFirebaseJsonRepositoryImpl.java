package com.diary.inn.InnDiary.repository.firebase;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.utils.firebase.FirebaseFetcher;
import com.diary.inn.InnDiary.utils.meta.SlotMeta;
import com.diary.inn.InnDiary.utils.meta.TodoMeta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Qualifier("TodoFirebaseJsonServiceImpl")
@Repository
@RequiredArgsConstructor
@Slf4j
public class TodoFirebaseJsonRepositoryImpl implements TodoFirebaseJsonRepository {
    private final FirebaseFetcher firebaseFetcher;
    private Map<Integer, List<TodoMeta>> todoDataAll = new HashMap<>();

    @Override
    public List<Slot> processAllSlotFromFirebaseData(User u) {
        List<Slot> rs = new ArrayList<>();

        for (Integer i :isAnySlotHasData()) {
//            SlotMeta s = settingSlotMeta(firebaseDataRepository.getWhichSlotNumData("todoSlot", "slot" + i), i);
//
//            rs.add(Slot.builder()
//                    .slotNum(i)
//                    .userSeq(u.getId())
//                    .userEmail(u.getEmail())
//                    .userName(u.getName())
//                    .userUid(u.getUid())
//                    .title(s.getTitle())
//                    .which("todo")
//                    .modDate(stringToLocalDate(s.getModDate()))
//                    .build());
        }
        return rs;
    }

    private SlotMeta settingSlotMeta(DataSnapshot ds, int i) {
        GenericTypeIndicator<List<TodoMeta>> tD = new GenericTypeIndicator<List<TodoMeta>>() {};

        SlotMeta s = ds.getValue(SlotMeta.class);
        List<TodoMeta> tList = ds.child("todoList").getValue(tD);

        todoDataAll.put(i, tList);

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
    public Map<Integer, List<TodoMeta>> getTodoWithSlot() {
        return this.todoDataAll;
    }

    @Override
    public List<Integer> isAnySlotHasData() {
        List<Integer> isSlotExists = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
//            if (firebaseDataRepository.getWhichData("todoSlot").hasChild("slot" + i)) {
//                isSlotExists.add(i);
//            }
        }
        return isSlotExists;
    }
}
