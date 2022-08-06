package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.QToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.entity.info.QMemberEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Qualifier("ModifyToDoJsonRepositoryImpl")
@Repository
@Slf4j
public class ModifyToDoJsonRepositoryImpl extends QuerydslRepositorySupport implements ModifyToDoJsonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QToDoJsonEntity toDoJsonEntity = QToDoJsonEntity.toDoJsonEntity;
    private final QMemberEntity memberEntity = QMemberEntity.memberEntity;

    public ModifyToDoJsonRepositoryImpl() {
        super(ToDoJsonEntity.class);
    }

    @Override
    public List<ToDoJsonEntity> findAllByEmailNCompany(MemberEntity mEntity) {
        JPQLQuery<ToDoJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(memberEntity.seq.eq(mEntity.getSeq()));
        return jpqlQuery.fetch();
    }

    @Override
    public ToDoJsonEntity findByEmailNCompanyWithSave(MemberEntity mEntity, String saveTitle) {
        JPQLQuery<ToDoJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(memberEntity.loginId.eq(mEntity.getLoginId()).and(memberEntity.company.eq(mEntity.getCompany())).and(toDoJsonEntity.saveTitle.eq(saveTitle)));
        return jpqlQuery.fetchOne();
    }

    private JPQLQuery<ToDoJsonEntity> jpaQueryBuilder() {
        JPQLQuery<ToDoJsonEntity> jpqlQuery = from(toDoJsonEntity);
        jpqlQuery.leftJoin(memberEntity).on(toDoJsonEntity.member.eq(memberEntity));
        return jpqlQuery;
    }

    @Transactional
    @Override
    public void updateSaveTitle(Long seq, String saveTitle) {
        log.info("value check : {}, {}", seq, saveTitle);
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();

        jpaQueryFactory
                .update(toDoJsonEntity)
                .where(toDoJsonEntity.seq.eq(seq))
                .set(toDoJsonEntity.saveTitle, saveTitle)
                .execute();
    }

    @Transactional
    @Override
    public void updateJson(Long seq, String json) {
        log.info("value check : {}, {}", seq, json);
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();

        jpaQueryFactory
                .update(toDoJsonEntity)
                .where(toDoJsonEntity.seq.eq(seq))
                .set(toDoJsonEntity.todo, json)
                .execute();
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }
}
