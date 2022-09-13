package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.QDiaryEntity;
import com.diary.inn.InnDiary.work.entity.QSlotEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
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

@Qualifier("SearchDiaryRepositoryImpl")
@Repository
public class SearchDiaryRepositoryImpl extends QuerydslRepositorySupport implements SearchDiaryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QDiaryEntity diaryEntity = QDiaryEntity.diaryEntity;
    private final QSlotEntity slotEntity = QSlotEntity.slotEntity;

    public SearchDiaryRepositoryImpl() {
        super(DiaryEntity.class);
    }

    @Override
    public DiaryEntity findBySlotNSeq(SlotEntity se, Long seq) {
        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryEntity.slot.eq(se).and(diaryEntity.diarySeq.eq(seq)));
        return jpqlQuery.fetchOne();
    }

    @Override
    public List<DiaryEntity> findAllBySlot(SlotEntity se) {
        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryEntity.slot.eq(se));
        return jpqlQuery.fetch();
    }

    @Override
    public DiaryEntity findByDate(String date) {
        return null;
    }

    @Override
    public List<DiaryEntity> findByFromDateToDate(String date) {
        return null;
    }

    @Transactional
    @Override
    public void updateDiary(DiaryEntity de) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();


        jpaQueryFactory.update(diaryEntity)
                .set(diaryEntity.title, de.getTitle())
                .set(diaryEntity.date, de.getDate())
                .set(diaryEntity.status, de.getStatus())
                .set(diaryEntity.weather, de.getWeather())
                .set(diaryEntity.content, de.getContent())
                .where(diaryEntity.dSeq.eq(de.getDSeq()))
                .execute();
    }


    @Transactional
    @Override
    public void deleteBySlot(Long seq) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
        jpaQueryFactory.delete(diaryEntity)
                .where(diaryEntity.slot.in(
                        from(slotEntity)
                                .where(diaryEntity.slot.seq.eq(seq))
                        )
                ).execute();
    }

    private JPQLQuery<DiaryEntity> jpaQueryBuilder() {
        JPQLQuery<DiaryEntity> jpqlQuery = from(diaryEntity);
        jpqlQuery.leftJoin(slotEntity).on(diaryEntity.slot.eq(slotEntity));
        return jpqlQuery;
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }
}
