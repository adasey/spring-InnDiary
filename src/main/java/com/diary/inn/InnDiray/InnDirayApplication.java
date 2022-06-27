package com.diary.inn.InnDiray;

import com.diary.inn.InnDiray.config.properties.AppProperties;
import com.diary.inn.InnDiray.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class InnDirayApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnDirayApplication.class, args);
	}

}
