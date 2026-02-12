package com.company.backend;

import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import com.company.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner test(UserService userService){
		return args -> {
			userService.runTest();
		};
	}
}
