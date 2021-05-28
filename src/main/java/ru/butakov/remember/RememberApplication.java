package ru.butakov.remember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "ru.butakov.remember")
@EnableAsync
public class RememberApplication {

	public static void main(String[] args) {
		SpringApplication.run(RememberApplication.class, args);
	}

}
