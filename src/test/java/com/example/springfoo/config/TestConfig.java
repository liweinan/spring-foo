package com.example.springfoo.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestConfiguration
public class TestConfig {

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
//        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1");
        registry.add("spring.r2dbc.username", () -> "sa");
        registry.add("spring.r2dbc.password", () -> "password");
    }
} 