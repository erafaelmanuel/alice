package com.remswork.project.alice;

import com.remswork.project.alice.model.Activity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.remswork.project.alice")
public class AliceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AliceApplication.class, args);
	}
}
