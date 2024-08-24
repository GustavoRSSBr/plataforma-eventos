package com.todoseventos.todos_eventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.todoseventos.todos_eventos"})
@EnableAsync
public class Main {

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);
	}

}
