package com.example.innosistemas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.innosistemas.repository")
public class InnoSistemasApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnoSistemasApplication.class, args);
	}

}
