package ru.nuyaxov.pzviewha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Отмечает что данный класс является корневым классом для конфигурирования контекста Spring Boot
@SpringBootApplication()
public class PzviewhaApplication {

	//Если не передать 'args' - мы не сможем передавать аргументы настройки, например из cmd
	public static void main(String[] args) {
		SpringApplication.run(PzviewhaApplication.class, args);
	}
}
