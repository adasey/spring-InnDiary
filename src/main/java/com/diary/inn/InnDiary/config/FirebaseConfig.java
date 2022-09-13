package com.diary.inn.InnDiary.config;

import com.diary.inn.InnDiary.work.repository.firebase.FirebaseJsonRepository;
import com.diary.inn.InnDiary.work.service.json.DiaryFirebaseJsonService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FirebaseConfig {
    private final FirebaseJsonRepository firebaseJsonRepository;

    @PostConstruct
    public void init() {
        firebaseAppInitialSetting();
        fetchValueFromFirebase();
    }

    private void firebaseAppInitialSetting() {
        String DATABASE_URL = "https://whyitisnotrunning-default-rtdb.firebaseio.com/";

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(firebaseSettingFromJsonFile()))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileInputStream firebaseSettingFromJsonFile() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/whyitisnotrunning.json");
    }

    private void fetchValueFromFirebase() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference ref = fd.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot document = dataSnapshot.child("users").child("uid");
                System.out.println("result value = " + document.getValue());
                firebaseJsonRepository.setFirebaseData(document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("went wrong : " + error.getCode());
            }
        });
    }
}