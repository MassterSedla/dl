package com.example.dlSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class DlApplication {
    public static void main(String[] args) {

        SpringApplication.run(DlApplication.class, args);
    }
}

// Метод main() - точка входа в программу. Когда запускается наше Spring Boot приложение,
// то автоматически создаётся Spring Container, происходит сканирование и поиск бинов и регистрация их
// в Spring Container. После чего поднимается Tomcat сервер, на котором и запускается наше приложение.