package com.diary.inn.InnDiary.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Configuration
public class FirebaseConfig {

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
    }

    private FileInputStream getFirebaseSettingFromJsonFile() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/inndiary-loginconnect.json");
    }
}