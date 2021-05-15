package ru.butakov.remember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.butakov.remember")
public class RememberApplication {

	public static void main(String[] args) {
		SpringApplication.run(RememberApplication.class, args);
	}

}
