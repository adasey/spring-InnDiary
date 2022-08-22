package com.diary.inn.InnDiary.work.repository.info;

import com.diary.inn.InnDiary.work.entity.api.QDiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.QDiaryEntity;
import com.diary.inn.InnDiary.work.entity.info.DiaryEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Qualifier("SearchDiaryRepositoryImpl")
@Repository
public class SearchDiaryRepositoryImpl extends QuerydslRepositorySupport implements SearchDiaryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QDiaryEntity diaryEntity = QDiaryEntity.diaryEntity;
    private final QDiaryJsonEntity diaryJsonEntity = QDiaryJsonEntity.diaryJsonEntity;

    public SearchDiaryRepositoryImpl() {
        super(DiaryEntity.class);
    }

    @Override
    public List<DiaryEntity> diaryByDiaryJson(Long seq) {
        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        return jpqlQuery.where(diaryEntity.diary.seq.eq(seq)).fetch();
    }

    @Override
    public void deleteByDiaryJsonSeq(Long seq) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();

        jpaQueryFactory.delete(diaryEntity)
                .where(diaryEntity.diary.in(
                        from(diaryJsonEntity)
                                .where(diaryJsonEntity.seq.eq(seq))
                        )
                )
                .execute();
    }

    private JPQLQuery<DiaryEntity> jpaQueryBuilder() {
        JPQLQuery<DiaryEntity> jpqlQuery = from(diaryEntity);
        jpqlQuery.leftJoin(diaryJsonEntity).on(diaryEntity.diary.eq(diaryJsonEntity));
        return jpqlQuery;
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }
}
