package com.diary.inn.InnDiary.config;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.login.repository.UserRepository;
import com.diary.inn.InnDiary.login.service.UserService;
import com.diary.inn.InnDiary.work.repository.bef.FirebaseJsonRepository;
import com.diary.inn.InnDiary.work.repository.bef.ModifyDiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.bef.ModifyTodoJsonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FirebaseConfig {
    private final FirebaseJsonRepository firebaseJsonRepository;

    @PostConstruct
    public void init() {
        String DATABASE_URL = "https://inndiary-loginconnect-default-rtdb.firebaseio.com/";

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(getFirebaseSettingFromJsonFile()))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getValueFromFirebase();
    }

    private void getValueFromFirebase() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference ref = fd.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot document = dataSnapshot.child("users_post");
                System.out.println("result value = " + document.getValue());
                firebaseJsonRepository.setInitFirebaseJson(document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("went wrong : " + error.getCode());
            }
        });
    }

    private FileInputStream getFirebaseSettingFromJsonFile() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/inndiary-loginconnect.json");
    }
}