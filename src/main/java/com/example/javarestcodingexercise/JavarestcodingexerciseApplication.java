package com.example.javarestcodingexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class JavarestcodingexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavarestcodingexerciseApplication.class, args);
	}

}
