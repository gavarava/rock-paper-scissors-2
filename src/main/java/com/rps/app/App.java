package com.rps.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        log.info("Running Rock Paper Scissors");
        SpringApplication.run(App.class, args);
    }
}
