package com.diary.inn.InnDiray.user.repository;

import com.diary.inn.InnDiray.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userId = :userId")
    User findUserIdBy(@Param("userId") String userId);
}
