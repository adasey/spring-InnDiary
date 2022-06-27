package com.diary.inn.InnDiray.user.repository;

import com.diary.inn.InnDiray.user.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    @Query("select ur from UserRefreshToken ur where ur.userId = :userId")
    UserRefreshToken findByUserId(@Param("userId") String userId);

    @Query("select ur from UserRefreshToken ur where ur.userId = :userId and ur.refreshToken = :refreshToken")
    UserRefreshToken findByUserIdAndRefreshToken(@Param("userId") String userId, @Param("refreshToken") String refreshToken);
}
