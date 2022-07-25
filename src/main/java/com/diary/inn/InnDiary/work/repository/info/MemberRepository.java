package com.diary.inn.InnDiary.work.repository.info;

import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>, QuerydslPredicateExecutor<MemberEntity> {
    @Query("select m from MemberEntity m where m.loginId = :email")
    MemberEntity findByLoginId(@Param("email") String loginId);

    @Query("select m from MemberEntity m where m.loginId = :email and m.company = :company")
    MemberEntity findByLoginIdNCompany(@Param("email") String loginId, @Param("company") int company);

    @Modifying(clearAutomatically = true) // entityManager can't flush and it is't change works after update did. in db level it works fine
    // if you want change works, use clearAutomatically = true for use entityManager flush
    @Query("update MemberEntity m set m.state = :state where m.seq = :seq")
    void updateMemberState(@Param("seq") long seq, @Param("state") int state);
}
