package com.wty.flowengine.engine;

import handler.DocumentUpdateHandler;
import handler.Task1Service;
import handler.Task2Service;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestConfiguration {
    @Bean
    DocumentUpdateHandler documentUpdateHandler() {
        return new DocumentUpdateHandler();
    }

    @Bean
    Task1Service task1Service() {
        return new Task1Service();
    }

    @Bean
    Task2Service task2Service() {
        return new Task2Service();
    }
}
