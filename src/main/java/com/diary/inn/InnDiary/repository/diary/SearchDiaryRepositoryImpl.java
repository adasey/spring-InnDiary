package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.QDiaryEntity;
import com.diary.inn.InnDiary.work.entity.QSlotEntity;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Qualifier("SearchDiaryRepositoryImpl")
@Repository
@Slf4j
public class SearchDiaryRepositoryImpl extends QuerydslRepositorySupport implements SearchDiaryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QDiaryEntity diaryEntity = QDiaryEntity.diaryEntity;
    private final QSlotEntity slotEntity = QSlotEntity.slotEntity;

    private SlotEntity sEntity = null;

    public SearchDiaryRepositoryImpl() {
        super(DiaryEntity.class);
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
    public DiaryEntity findBySeq(Long seq) {
        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryEntity.slot.eq(sEntity).and(diaryEntity.diarySeq.eq(seq)));
        return jpqlQuery.fetchOne();
    }

    @Override
    public List<DiaryEntity> findAllBySlot() {
        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryEntity.slot.eq(sEntity));
        return jpqlQuery.fetch();
    }

    @Override
    public List<DiaryEntity> findByMonthDate(LocalDate date) {
        LocalDate sDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate eDate = LocalDate.of(date.getYear(), date.getMonth(), date.lengthOfMonth());

        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryEntity.slot.seq.eq(sEntity.getSeq())
                .and(diaryEntity.date.goe(sDate)
                        .and(diaryEntity.date.loe(eDate))));
        return jpqlQuery.fetch();
    }

    @Override
    public List<DiaryEntity> findByBetweenMonthDate(LocalDate startDate, LocalDate endDate) {
        JPQLQuery<DiaryEntity> jpqlQuery = jpaQueryBuilder();
        jpqlQuery.where(diaryEntity.slot.seq.eq(sEntity.getSeq())
                .and(diaryEntity.date.goe(startDate)
                        .and(diaryEntity.date.loe(endDate))));
        return jpqlQuery.fetch();
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
