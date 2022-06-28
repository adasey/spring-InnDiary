package com.diary.inn.InnDiray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class InnDirayApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnDirayApplication.class, args);
	}

}
