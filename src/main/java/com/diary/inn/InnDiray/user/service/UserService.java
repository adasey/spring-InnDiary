package com.diary.inn.InnDiray.user.service;

import com.diary.inn.InnDiray.user.entity.User;
import com.diary.inn.InnDiray.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findUserIdBy(userId);
    }
}
