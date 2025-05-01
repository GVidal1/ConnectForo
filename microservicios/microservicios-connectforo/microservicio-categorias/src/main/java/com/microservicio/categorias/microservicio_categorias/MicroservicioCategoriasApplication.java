package com.microservicio.categorias.microservicio_categorias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicioCategoriasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCategoriasApplication.class, args);
	}

}
