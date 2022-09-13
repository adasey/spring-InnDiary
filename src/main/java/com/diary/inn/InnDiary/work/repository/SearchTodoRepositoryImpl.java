package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Qualifier("SearchTodoRepositoryImpl")
@Repository
public class SearchTodoRepositoryImpl extends QuerydslRepositorySupport implements SearchTodoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QTodoEntity todoEntity = QTodoEntity.todoEntity;
    private final QSlotEntity slotEntity = QSlotEntity.slotEntity;

    public SearchTodoRepositoryImpl() {
        super(TodoEntity.class);
    }

    @Override
    public TodoEntity findBySlotNSeq(SlotEntity se, Long seq) {
        JPQLQuery<TodoEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoEntity.slot.eq(se).and(todoEntity.todoSeq.eq(seq)));
        return jpqlQuery.fetchOne();
    }

    @Override
    public List<TodoEntity> findAllBySlot(SlotEntity se) {
        JPQLQuery<TodoEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoEntity.slot.eq(se));
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
