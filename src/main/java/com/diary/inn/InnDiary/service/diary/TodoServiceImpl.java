package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.entity.UserEntity;
import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.domain.diary.Todo;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import com.diary.inn.InnDiary.entity.diary.TodoEntity;
import com.diary.inn.InnDiary.repository.diary.SearchTodoRepository;
import com.diary.inn.InnDiary.repository.diary.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService, TodoConvertService, CommonPerform {
    private final TodoRepository todoRepository;
    private final SearchTodoRepository searchTodoRepository;

    @Override
    public Todo entityToDto(TodoEntity te) {
        return Todo.builder()
                .tSeq(te.getTSeq())
                .todoSeq(te.getTodoSeq())
                .title(te.getTitle())
                .date(te.getDate())
                .content(te.getContent())
                .slotSeq(te.getSlot().getSeq())
                .build();
    }

    @Override
    public TodoEntity dtoToEntity(Todo t) {
        SlotEntity se = SlotEntity.builder()
                .seq(t.getSlotSeq())
                .build();

        return TodoEntity.builder()
                .tSeq(t.getTSeq())
                .todoSeq(t.getTodoSeq())
                .title(t.getTitle())
                .date(t.getDate())
                .content(t.getContent())
                .slot(se)
                .build();
    }

    @Override
    public void setWantSlot(Slot slot) {
        searchTodoRepository.setSlot(slotToEntity(slot));
    }

    @Override
    public Slot getNowSlot() {
        return slotEntityToDto(searchTodoRepository.getSlot());
    }

    @Override
    public boolean isSlotSetting() {
        return searchTodoRepository.isSlotSetting();
    }

    @Override
    public Todo createTodo(Todo todo) {
        return entityToDto(todoRepository.save(dtoToEntity(todo)));
    }

    @Override
    public Todo findTodoBySeq(Long todoSeq) {
        if (searchTodoRepository.isSlotSetting()) {
            return entityToDto(searchTodoRepository.findBySeq(todoSeq));
        }
        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findAllTodoBySlot() {
        if (searchTodoRepository.isSlotSetting()) {
            List<Todo> found = new ArrayList<>();
            List<TodoEntity> fTodo = searchTodoRepository.findAllBySlot();

            for (TodoEntity te : fTodo) {
                found.add(entityToDto(te));
            }
            return found;
        }
        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findMonthTodo(LocalDate date) {
        if (searchTodoRepository.isSlotSetting()) {
            List<Todo> result = new ArrayList<>();
            List<TodoEntity> todoByDate = searchTodoRepository.findByMonthDate(date);

            for (TodoEntity te : todoByDate) {
                result.add(entityToDto(te));
            }
            return result;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findBetweenMonthTodo(LocalDate startDate, LocalDate endDate) {
        if (searchTodoRepository.isSlotSetting()) {
            List<LocalDate> dates = splitDate(startDate, endDate);
            List<Todo> result = new ArrayList<>();

            for (int i = 0; i < dates.size(); i += 2) {
                List<TodoEntity> betweenMonthDiary = searchTodoRepository.findByBetweenMonthDate(dates.get(i), dates.get(i + 1));

                for (TodoEntity te : betweenMonthDiary) {
                    result.add(entityToDto(te));
                }
            }

            return result;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public void updateTodo(Todo todo) {
        searchTodoRepository.updateTodo(dtoToEntity(todo));
    }

    @Override
    public void deleteTodo(Todo todo) {
        todoRepository.delete(dtoToEntity(todo));
    }

    @Override
    public void deleteTodoBySlot(Long slotSeq) {
        searchTodoRepository.deleteBySlot(slotSeq);
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

    @Override
    public List<LocalDate> splitDate(LocalDate sDate, LocalDate eDate) {
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

    @Override
    public List<LocalDate> insertSplitMonth(LocalDate ld, Period p) {
        List<LocalDate> r = new ArrayList<>();

        if (isMonthMoreThenOne(p)) {
            for (int i = 1; i <= p.getMonths(); i++) {
                ld = ld.plusMonths(1);
                r.add(ld);
                r.add(LocalDate.of(ld.getYear(), ld.getMonth(), ld.lengthOfMonth()));
            }
        }

        return r;
    }

    @Override
    public boolean isMonthMoreThenOne(Period p) {
        return p.getMonths() > 0;
    }
}
