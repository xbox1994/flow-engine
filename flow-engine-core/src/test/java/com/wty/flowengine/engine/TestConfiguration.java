package com.wty.flowengine.engine;

import handler.DocumentUpdateHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestConfiguration {
    @Bean
    DocumentUpdateHandler documentUpdateHandler() {
        return new DocumentUpdateHandler();
    }
}
