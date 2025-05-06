package com.microservicio.soporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioSoporteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioSoporteApplication.class, args);
    }
} 