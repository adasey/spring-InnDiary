package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.QSlotEntity;
import com.diary.inn.InnDiary.entity.diary.QTodoEntity;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import com.diary.inn.InnDiary.entity.diary.TodoEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Qualifier("SearchTodoRepositoryImpl")
@Repository
public class SearchTodoRepositoryImpl extends QuerydslRepositorySupport implements SearchTodoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QTodoEntity todoEntity = QTodoEntity.todoEntity;
    private final QSlotEntity slotEntity = QSlotEntity.slotEntity;

    private SlotEntity sEntity = null;

    public SearchTodoRepositoryImpl() {
        super(TodoEntity.class);
    }

    @Override
    public void setSlot(SlotEntity se) {
        sEntity = se;
    }

    @Override
    public SlotEntity getSlot() {
        return sEntity;
    }

    @Override
    public boolean isSlotSetting() {
        return sEntity != null;
    }

    @Override
    public TodoEntity findBySeq(Long seq) {
        JPQLQuery<TodoEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoEntity.slot.eq(sEntity).and(todoEntity.todoSeq.eq(seq)));
        return jpqlQuery.fetchOne();
    }

    @Override
    public List<TodoEntity> findAllBySlot() {
        JPQLQuery<TodoEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoEntity.slot.eq(sEntity));
        return jpqlQuery.fetch();
    }

    @Override
    public List<TodoEntity> findByMonthDate(LocalDate date) {
        LocalDate sDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate eDate = LocalDate.of(date.getYear(), date.getMonth(), date.lengthOfMonth());

        JPQLQuery<TodoEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoEntity.slot.seq.eq(sEntity.getSeq())
                .and(todoEntity.date.goe(sDate)
                        .and(todoEntity.date.loe(eDate))));
        return jpqlQuery.fetch();
    }

    @Override
    public List<TodoEntity> findByBetweenMonthDate(LocalDate startDate, LocalDate endDate) {
        JPQLQuery<TodoEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoEntity.slot.seq.eq(sEntity.getSeq())
                .and(todoEntity.date.goe(startDate)
                        .and(todoEntity.date.loe(endDate))));
        return jpqlQuery.fetch();
    }

    @Transactional
    @Override
    public void updateTodo(TodoEntity te) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();

        jpaQueryFactory.update(todoEntity)
                .set(todoEntity.title, te.getTitle())
                .set(todoEntity.date, te.getDate())
                .set(todoEntity.content, te.getContent())
                .where(todoEntity.tSeq.eq(te.getTSeq()))
                .execute();
    }

    @Transactional
    @Override
    public void deleteBySlot(Long seq) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
        jpaQueryFactory.delete(todoEntity)
                .where(todoEntity.slot.in(
                                from(slotEntity)
                                        .where(todoEntity.slot.seq.eq(seq))
                        )
                ).execute();
    }

    private JPQLQuery<TodoEntity> jpaQueryBuilder() {
        JPQLQuery<TodoEntity> jpqlQuery = from(todoEntity);
        jpqlQuery.leftJoin(slotEntity).on(todoEntity.slot.eq(slotEntity));
        return jpqlQuery;
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }
}
