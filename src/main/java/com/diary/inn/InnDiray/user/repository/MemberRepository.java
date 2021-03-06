package com.diary.inn.InnDiray.user.repository;

import com.diary.inn.InnDiray.user.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>, QuerydslPredicateExecutor<MemberEntity> {
    @Query("select m from MemberEntity m where m.loginId = :email")
    MemberEntity findByLoginId(@Param("email") String loginId);
}
