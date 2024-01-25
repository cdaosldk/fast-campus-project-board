package com.fastcampus.fastcampusprojectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class FastCampusProjectBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastCampusProjectBoardApplication.class, args);
//		SpringApplication.run(FastCampusProjectBoardApplication.class, args).close();
	}

}
