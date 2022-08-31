package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.bef.DBSlot;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.bef.QDiaryJsonEntity;
import com.google.firebase.database.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Qualifier("ModifyDiaryJsonRepositoryImpl")
@Repository
@Slf4j
public class ModifyDiaryJsonRepositoryImpl extends QuerydslRepositorySupport implements ModifyDiaryJsonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QDiaryJsonEntity diaryJsonEntity = QDiaryJsonEntity.diaryJsonEntity;

    public ModifyDiaryJsonRepositoryImpl() {
        super(DiaryJsonEntity.class);
    }

    private JPQLQuery<DiaryJsonEntity> jpaQueryBuilder() {
        return from(diaryJsonEntity);
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }

    @Override
    public List<DiaryJsonEntity> allSlotFoundByDiarySlot(String diarySlot) {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryJsonEntity.diarySlot.eq(diarySlot));
        return jpqlQuery.fetch();
    }

    @Override
    public DiaryJsonEntity slotFoundBySlotNum(String diarySlot, int slotNum) {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryJsonEntity.diarySlot.eq(diarySlot).and(diaryJsonEntity.slotNum.eq(slotNum)));
        return jpqlQuery.fetchOne();
    }

//    public void updateJson(Long seq, String json) {
//        log.info("value check : {}, {}", seq, json);
//        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
//
//        jpaQueryFactory
//                .update(diaryJsonEntity)
//                .where(diaryJsonEntity.seq.eq(seq))
//                .set(diaryJsonEntity.diary, json)
//                .execute();
//    }

// 만일 update 시 조건을 걸고 싶다면
//    private BooleanBuilder booleanBuilderDiaryJsonWithMemberEntityGenerator(DiaryJsonEntity entity) {
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        BooleanExpression saveTitle = diaryJsonEntity.saveTitle.eq(entity.getSaveTitle());
//        BooleanExpression login = diaryJsonEntity.member.seq.in(
//                JPAExpressions.select(memberEntity.seq)
//                        .from(memberEntity)
//                        .where(memberEntity.seq.eq(entity.getMember().getSeq()))
//        );
//
//        booleanBuilder.and(saveTitle).and(login);
//
//        return booleanBuilder;
//    }
}
