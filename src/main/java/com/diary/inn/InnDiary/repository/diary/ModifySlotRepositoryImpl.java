package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.entity.QUserEntity;
import com.diary.inn.InnDiary.entity.diary.QSlotEntity;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Qualifier("ModifySlotRepositoryImpl")
@Repository
public class ModifySlotRepositoryImpl extends QuerydslRepositorySupport implements ModifySlotRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QSlotEntity slotEntity = QSlotEntity.slotEntity;
    private final QUserEntity userEntity = QUserEntity.userEntity;

    public ModifySlotRepositoryImpl() {
        super(SlotEntity.class);
    }

    @Override
    public SlotEntity findWhichSlotByUserNNum(User user, String which, Integer num) {
        JPQLQuery<SlotEntity> jpaQuery = jpaQueryBuilder();
        jpaQuery.where(slotEntity.user.id.eq(user.getId()).and(slotEntity.which.eq(which)).and(slotEntity.slotNum.eq(num)));
        return jpaQuery.fetchOne();
    }

    @Override
    public List<SlotEntity> findWhichSlotByUser(User user, String which) {
        JPQLQuery<SlotEntity> jpaQuery = jpaQueryBuilder();
        jpaQuery.where(slotEntity.user.id.eq(user.getId()).and(slotEntity.which.eq(which)));
        return jpaQuery.fetch();
    }

    @Override
    public List<SlotEntity> findWhichSlotByUid(String uid, String which) {
        JPQLQuery<SlotEntity> jpaQuery = jpaQueryBuilder();
        jpaQuery.where(slotEntity.user.uid.eq(uid).and(slotEntity.which.eq(which)));
        return jpaQuery.fetch();
    }

    @Override
    public List<SlotEntity> findAllByUid(String uid) {
        JPQLQuery<SlotEntity> jpaQuery = jpaQueryBuilder();
        jpaQuery.where(slotEntity.user.uid.eq(uid));
        return jpaQuery.fetch();
    }

    @Transactional
    @Override
    public void updateSlotBySlotNum(SlotEntity se) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
        jpaQueryFactory.update(slotEntity)
                .where(slotEntity.user.in(
                        from(userEntity)
                                .where(userEntity.id.eq(se.getUser().getId()))
                        ).and(slotEntity.slotNum.eq(se.getSlotNum()))
                ).execute();
    }

    @Transactional
    @Override
    public void deleteByUser(Long seq) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
        jpaQueryFactory.delete(slotEntity)
                .where(slotEntity.user.in(
                        from(userEntity)
                                .where(userEntity.id.eq(seq))
                        )
                ).execute();
    }

    @Transactional
    @Override
    public void deleteBySlot(SlotEntity se) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
        jpaQueryFactory.delete(slotEntity)
                .where(slotEntity.user.in(
                            from(userEntity)
                                    .where(userEntity.id.eq(se.getUser().getId())
                            )
                        )
                ).execute();
    }

    private JPQLQuery<SlotEntity> jpaQueryBuilder() {
        JPQLQuery<SlotEntity> jpqlQuery = from(slotEntity);
        jpqlQuery.leftJoin(userEntity).on(slotEntity.user.eq(userEntity));
        return jpqlQuery;
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }
}
