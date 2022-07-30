package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.api.QDiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.entity.info.QMemberEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    private final QMemberEntity memberEntity = QMemberEntity.memberEntity;

    public ModifyDiaryJsonRepositoryImpl() {
        super(DiaryJsonEntity.class);
    }

    @Override
    public List<DiaryJsonEntity> findAllByEmailNCompany(MemberEntity mEntity) {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(memberEntity.seq.eq(mEntity.getSeq()));
        return jpqlQuery.fetch();
    }

    @Override
    public DiaryJsonEntity findByEmailNCompanyWithSave(MemberEntity mEntity, String saveTitle) {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(memberEntity.loginId.eq(mEntity.getLoginId()).and(memberEntity.company.eq(mEntity.getCompany())).and(diaryJsonEntity.saveTitle.eq(saveTitle)));
        return jpqlQuery.fetchOne();
    }

    private JPQLQuery<DiaryJsonEntity> jpaQueryBuilder() {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = from(diaryJsonEntity);
        jpqlQuery.leftJoin(memberEntity).on(diaryJsonEntity.member.eq(memberEntity));
        return jpqlQuery;
    }

    @Transactional
    @Override
    public void updateSaveTitle(Long seq, String saveTitle) {
        log.info("value check : {}, {}", seq, saveTitle);
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();

        jpaQueryFactory
                .update(diaryJsonEntity)
                .where(diaryJsonEntity.seq.eq(seq))
                .set(diaryJsonEntity.saveTitle, saveTitle)
                .execute();
    }

    @Transactional
    @Override
    public void updateJson(Long seq, String json) {
        log.info("value check : {}, {}", seq, json);
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();

        jpaQueryFactory
                .update(diaryJsonEntity)
                .where(diaryJsonEntity.seq.eq(seq))
                .set(diaryJsonEntity.diary, json)
                .execute();
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }

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
