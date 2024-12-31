package com.example.employee_reimbursement_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EmployeeReimbursementSystemApplication {

	public static void main(String[] args) {
		// // Load the .env file
		// Dotenv dotenv = Dotenv.load();

		// // Set environment variables to system properties
		// dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(),
		// 		entry.getValue()));

		SpringApplication.run(EmployeeReimbursementSystemApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

}
