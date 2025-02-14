package com.nttdata.transaction.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient clientCustomer() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    public WebClient clientAccount() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Bean
    public WebClient clientCredit() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }

    @Bean
    public WebClient clientCreditCard() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083")
                .build();
    }
}