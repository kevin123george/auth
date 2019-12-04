package com.example.authtry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing
public class AuthtryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthtryApplication.class, args);
	}

}
