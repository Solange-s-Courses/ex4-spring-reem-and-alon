package com.example.ex4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for starting the Spring Boot application.
 * <p>
 * Entry point for the ex4 project.
 */
@SpringBootApplication
@EnableScheduling
public class Ex4Application {

	/**
	 * Application entry point.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Ex4Application.class, args);
	}
}
