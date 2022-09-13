package com.diary.inn.InnDiary.work.controller.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.login.repository.UserRepository;
import com.diary.inn.InnDiary.login.service.UserService;
import com.diary.inn.InnDiary.work.repository.firebase.FirebaseJsonRepository;
import com.diary.inn.InnDiary.work.service.json.DiaryFirebaseJsonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j // for test
@RequestMapping("/inn/apis/firebase/database")
@RestController
public class FirebaseDataController {
    private final FirebaseJsonRepository firebaseJsonRepository;
    private final DiaryFirebaseJsonService diaryFirebaseJsonService;
    private final UserRepository userRepository;

    private FirebaseDataController(FirebaseJsonRepository firebaseJsonRepository, DiaryFirebaseJsonService diaryFirebaseJsonService, UserRepository userRepository) {
        this.firebaseJsonRepository = firebaseJsonRepository;
        this.diaryFirebaseJsonService = diaryFirebaseJsonService;
        this.userRepository = userRepository;
    }

    // just test shortcut
    @GetMapping("/load")
    public String getFirebaseData() {
        Optional<UserEntity> find = userRepository.findByEmail("lmo9903@gmail.com");
        log.info("find value of user : {}", find);

        if (find.isPresent()) {
            firebaseJsonRepository.setValueByUid(find.get().getUid());
            if (firebaseJsonRepository.isDataFoundByUid()) log.info("value check by none found : {}", firebaseJsonRepository.getFirebaseData().getValue());

//            firebaseJsonRepository.setValueByUid(find.get().getUid());
//            log.info("test of value call diary : {}", diaryFirebaseJsonRepository.getDiaryOrTodoListOfData("diarySlot", "slot1"));
//            log.info("test of value call diary list num : {}", diaryFirebaseJsonRepository.getDiaryOrTodoListOfData("diarySlot", "slot1").child("diaryList").getChildren().iterator().next());
//            log.info("test of value call todo : {}", diaryFirebaseJsonRepository.getDiaryOrTodoListOfData("todoSlot", "slot1"));

            return "";
        }
        else {
            return "None";
        }
    }

    @GetMapping("/test")
    public String getDataSlot() {
        Optional<UserEntity> find = userRepository.findByEmail("lmo9903@gmail.com");

        if (find.isPresent()) {
//            firebaseJsonRepository.setValueByUid(find.get().getUid());
//            log.info("slot test of found : {}", diaryFirebaseJsonService.getAllSlotFromFirebaseData(find.get().getId()));
//            log.info("diary test of found : {}", diaryFirebaseJsonService.getDiaryWithSlot());
//            diaryFirebaseJsonRepository.setDataByUid(find.get().getUid());
//
//            Iterable<DataSnapshot> children = diaryFirebaseJsonRepository.getDiaryOrTodoListOfData("diarySlot", "slot1").child("diaryList").getChildren();

            int valueGuard = 0;

//            for (DataSnapshot child : children) {
//                valueGuard++;
//                if (valueGuard > 10) {
//                    break;
//                }
//
//                log.info("child next value test : {}", child);
//            }

            return "";
        }
        else {
            return "None";
        }
    }

}
