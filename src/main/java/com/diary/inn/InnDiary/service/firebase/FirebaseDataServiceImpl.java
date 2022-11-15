package com.diary.inn.InnDiary.service.firebase;

import com.diary.inn.InnDiary.domain.diary.*;
import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.utils.firebase.FirebaseFetcher;
import com.diary.inn.InnDiary.utils.meta.DiaryMeta;
import com.diary.inn.InnDiary.utils.meta.TodoMeta;
import com.diary.inn.InnDiary.repository.firebase.*;
import com.diary.inn.InnDiary.service.diary.*;
import com.google.firebase.database.DataSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Qualifier("FirebaseServiceImpl")
@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseDataServiceImpl implements FirebaseDataService {
    private final DiaryFirebaseJsonRepository diaryFirebaseJsonRepository;
    private final TodoFirebaseJsonRepository todoFirebaseJsonRepository;

    private final SlotService slotService;
    private final DiaryService diaryService;
    private final TodoService todoService;

    @Override
    public List<Slot> processDiary(User u) {
        return diaryFirebaseJsonRepository.processAllSlotFromFirebaseData(u);
    }

    @Override
    public List<Slot> processTodo(User u) {
        return todoFirebaseJsonRepository.processAllSlotFromFirebaseData(u);
    }

    @Override
    public Map<Integer, List<DiaryMeta>> getDiaryData() {
        return diaryFirebaseJsonRepository.getDiaryWithSlot();
    }

    @Override
    public Map<Integer, List<TodoMeta>> getTodoData() {
        return todoFirebaseJsonRepository.getTodoWithSlot();
    }

    @Override
    public List<Integer> diarySlotCheckList() {
        return diaryFirebaseJsonRepository.isAnySlotHasData();
    }

    @Override
    public List<Integer> todoSlotCheckList() {
        return todoFirebaseJsonRepository.isAnySlotHasData();
    }

    @Override
    public void insertAllSlots(User u) {
        List<Slot> dSlot = processDiary(u);
        List<Slot> tSlot = processTodo(u);

        insertLotsSlots(dSlot);
        insertLotsSlots(tSlot);
    }

    private void insertLotsSlots(List<Slot> slots) {
        for (Slot s : slots) {
            slotService.createSlot(s);
        }
    }

    @Override
    public void createAllSlotsData(User u) {
        Map<Integer, List<DiaryMeta>> diaryWithSlot = getDiaryData();
        Map<Integer, List<TodoMeta>> todoWithSlot = getTodoData();

        createDiarySlots(diaryWithSlot, u);
        createTodoSlots(todoWithSlot, u);
    }

    @Override
    public void createDiaryData(User u, int slotNum) {
        Slot dSlot = slotService.findWhichSlotByNum(u, "diary", slotNum);

        diaryService.deleteDiaryBySlot(dSlot.getSeq());
        createDiaryFromDiaryMeta(dSlot, getDiaryData().get(slotNum));
    }

    @Override
    public void createTodoData(User u, int slotNum) {
        Slot tSlot = slotService.findWhichSlotByNum(u, "todo", slotNum);

        todoService.deleteTodoBySlot(tSlot.getSeq());
        createTodoFromTodoMeta(tSlot, getTodoData().get(slotNum));
    }

    private void createDiarySlots(Map<Integer, List<DiaryMeta>> diarySlots, User u) {
        for (int i = 1; i <= 3; i++) {
            if (diarySlots.get(i) != null) {
                Slot ds = slotService.findWhichSlotByNum(u, "diary", i);
                createDiaryFromDiaryMeta(ds, diarySlots.get(i));
            }
        }
    }

    private void createDiaryFromDiaryMeta(Slot ds, List<DiaryMeta> diaryMetas) {
        for (DiaryMeta dm : diaryMetas) {
            Diary d = Diary.builder()
                    .diarySeq(dm.getSeq())
                    .title(dm.getTitle())
                    .status(dm.getStatus())
                    .weather(dm.getWeather())
                    .date(stringToDate(dm.getDate()))
                    .content(dm.getContent())
                    .slotSeq(ds.getSeq())
                    .build();

            diaryService.createDiary(d);
        }
    }

    private void createTodoSlots(Map<Integer, List<TodoMeta>> todoSlots, User u) {
        for (int i = 1; i <= 3; i++) {
            if (todoSlots.get(i) != null) {
                Slot ts = slotService.findWhichSlotByNum(u, "todo", i);
                createTodoFromTodoMeta(ts, getTodoData().get(i));
            }
        }
    }

    private void createTodoFromTodoMeta(Slot ts, List<TodoMeta> todoMetas) {
        for (TodoMeta tm : todoMetas) {
            Todo t = Todo.builder()
                    .todoSeq(tm.getSeq())
                    .title(tm.getTitle())
                    .date(stringToDate(tm.getDate()))
                    .content(tm.getContent())
                    .slotSeq(ts.getSeq())
                    .build();

            todoService.createTodo(t);
        }
    }

    private LocalDate stringToDate(String t) {
        LocalDate time = null;

        if (t.length() > 8) {
            time = LocalDate.parse(t, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }
        else {
            time = LocalDate.parse(t, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return time;
    }
}
