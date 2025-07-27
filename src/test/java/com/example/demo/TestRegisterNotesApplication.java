package com.example.demo;

import org.springframework.boot.SpringApplication;

public class TestRegisterNotesApplication {

	public static void main(String[] args) {
		SpringApplication.from(RegisterNotesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
