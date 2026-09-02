package com.firstmultibranch.jenkins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

@RestController
public class MultibranchjenkinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultibranchjenkinsApplication.class, args);
	}

	@GetMapping("/main")
	public String testSaludoMain(){
		return "Saludando desde main 14";
	}

}
