package com.microservicio.foros.microservicio_foros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicioForosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioForosApplication.class, args);
	}

}
