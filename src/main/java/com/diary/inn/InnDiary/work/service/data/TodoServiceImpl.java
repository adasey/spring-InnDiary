package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.entity.TodoEntity;
import com.diary.inn.InnDiary.work.repository.SearchTodoRepository;
import com.diary.inn.InnDiary.work.repository.TodoRepository;
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
public class TodoServiceImpl implements TodoService, TodoConversionService {
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
    public Todo createTodo(Todo todo) {
        return entityToDto(todoRepository.save(dtoToEntity(todo)));
    }

    @Override
    public Todo findTodoBySlotNSeq(Slot slot, Long todoSeq) {
        return entityToDto(searchTodoRepository.findBySlotNSeq(slotToEntity(slot), todoSeq));
    }

    @Override
    public List<Todo> findAllTodoBySlot(Slot slot) {
        List<Todo> found = new ArrayList<>();
        List<TodoEntity> fTodo = searchTodoRepository.findAllBySlot(slotToEntity(slot));

        for (TodoEntity te : fTodo) {
            found.add(entityToDto(te));
        }
        return found;
    }

    @Override
    public List<Todo> findMonthTodo(LocalDate date) {

        List<Todo> result = new ArrayList<>();
        List<TodoEntity> todoByDate = searchTodoRepository.findByMonthDate(date);

        for (TodoEntity te : todoByDate) {
            result.add(entityToDto(te));
        }
        return result;
    }

    @Override
    public Map<LocalDate, List<Todo>> findSixMonthTodo(LocalDate date) {
        Map<LocalDate, List<Todo>> result = new HashMap<>();
        List<Todo> rTodo = new ArrayList<>();

        for (int i = -2; i <= 3; i++) {
            LocalDate tDate = null;
            if (i <= 0) {
                tDate = date.minusMonths(-i);
            }
            else {
                tDate = date.plusMonths(i);
            }

            List<TodoEntity> findMonthDate = searchTodoRepository.findByMonthDate(tDate);

            for (TodoEntity te : findMonthDate) {
                rTodo.add(entityToDto(te));
            }

            result.put(tDate, rTodo);
            rTodo.clear();
        }

        return result;
    }

    @Override
    public Map<LocalDate, List<Todo>> findBetweenMonthTodo(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = splitDate(startDate, endDate);
        Map<LocalDate, List<Todo>> result = new HashMap<>();
        List<Todo> rt = new ArrayList<>();

        for (int i = 0; i < dates.size(); i += 2) {
            List<TodoEntity> betweenMonthDiary = searchTodoRepository.findByBetweenMonthDate(dates.get(i), dates.get(i + 1));

            for (TodoEntity te : betweenMonthDiary) {
                rt.add(entityToDto(te));
            }

            result.put(dates.get(i), rt);
            rt.clear();
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
}
