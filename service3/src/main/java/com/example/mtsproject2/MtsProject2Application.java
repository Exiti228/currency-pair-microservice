package com.example.mtsproject2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class MtsProject2Application {

    public static void main(String[] args) {
        SpringApplication.run(MtsProject2Application.class, args);
    }

}
