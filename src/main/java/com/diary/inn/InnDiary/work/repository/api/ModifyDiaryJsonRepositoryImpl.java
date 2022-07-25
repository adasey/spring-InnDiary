package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.api.QDiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.QMemberEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
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
    public List<DiaryJsonEntity> findAllByEmail(String email) {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = from(diaryJsonEntity);
        jpqlQuery.where(diaryJsonEntity.user.loginId.eq(email));
        return jpqlQuery.fetch();
    }

    @Override
    public DiaryJsonEntity findByEmailNSave(String email, String save) {
        JPQLQuery<DiaryJsonEntity> jpqlQuery = from(diaryJsonEntity);
        jpqlQuery.where(diaryJsonEntity.user.loginId.eq(email).and(diaryJsonEntity.saveTitle.eq(save)));
        return jpqlQuery.fetchOne();
    }

    @Transactional
    @Modifying
    @Override
    public void updateJsonByEmailNSave(DiaryJsonEntity entity) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression saveTitle = diaryJsonEntity.saveTitle.eq(entity.getSaveTitle());
        BooleanExpression login = diaryJsonEntity.user.seq.in(
                JPAExpressions.select(memberEntity.seq)
                        .from(memberEntity)
                        .where(memberEntity.loginId.eq(entity.getUser().getLoginId()))
        );

        booleanBuilder.and(saveTitle).and(login);

        jpaQueryFactory
                .update(diaryJsonEntity)
                .where(booleanBuilder)
                .set(diaryJsonEntity.diary, entity.getDiary())
                .execute();
    }
}
