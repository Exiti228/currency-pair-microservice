package com.example.mtsproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationBeans {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
