package com.diary.inn.InnDiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(AppProperties.class)
public class InnDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnDiaryApplication.class, args);
	}

}