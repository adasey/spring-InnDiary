package com.diary.inn.InnDiary.work.controller.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.login.repository.UserRepository;
import com.diary.inn.InnDiary.work.domain.bef.DataSlot;
import com.diary.inn.InnDiary.work.repository.bef.FirebaseJsonRepository;
import com.diary.inn.InnDiary.work.repository.bef.ModifyDiaryJsonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j // for test
@RequestMapping("/inn/apis/firebase/database")
@RequiredArgsConstructor
@RestController
public class FirebaseDataController {
    private final FirebaseJsonRepository firebaseJsonRepository;
    private final UserRepository userRepository;

    @GetMapping("/load")
    public String getFirebaseData() {
        Optional<UserEntity> find = userRepository.findByEmail("lmo9903@gmail.com");

        if (find.isPresent()) {
            DataSlot value = firebaseJsonRepository.getDataByUid(find.get().getUid()).getValue(DataSlot.class);
            log.info("data of finding by uid : {}", firebaseJsonRepository.getDataByUid(find.get().getUid()));
            log.info("data of finding by value of diary : {}", value.getDiary_slot());
            log.info("data of finding by value of todo : {}", value.getTodo_slot());

            return (String) firebaseJsonRepository.getDataByUid(find.get().getUid()).getValue();
        }
        else {
            return "None";
        }
    }
}
