package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.bef.QTodoJsonEntity;
import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Qualifier("ModifyTodoJsonRepositoryImpl")
@Repository
@Slf4j
public class ModifyTodoJsonRepositoryImpl extends QuerydslRepositorySupport implements ModifyTodoJsonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QTodoJsonEntity todoJsonEntity = QTodoJsonEntity.todoJsonEntity;

    private Object firebase;

    public ModifyTodoJsonRepositoryImpl() {
        super(TodoJsonEntity.class);
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }

    @Override
    public void setInitFirebaseJson(Object fj) {
        this.firebase = fj;
    }

    @Override
    public List<DiaryJsonEntity> findDataFromFirebase(UserEntity user) {
        return null;
    }

    @Override
    public List<TodoJsonEntity> allSlotFoundByToDoSlot(String todoSlot) {
        JPQLQuery<TodoJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoJsonEntity.todoSlot.eq(todoSlot));
        return jpqlQuery.fetch();
    }

    @Override
    public TodoJsonEntity slotFoundBySlotNum(String todoSlot, int slotNum) {
        JPQLQuery<TodoJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(todoJsonEntity.todoSlot.eq(todoSlot).and(todoJsonEntity.slotNum.eq(slotNum)));
        return jpqlQuery.fetchOne();
    }

    private JPQLQuery<TodoJsonEntity> jpaQueryBuilder() {
        return from(todoJsonEntity);
    }
}
