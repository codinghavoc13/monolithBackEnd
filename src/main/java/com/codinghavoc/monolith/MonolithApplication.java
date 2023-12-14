package com.codinghavoc.monolith;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class MonolithApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
