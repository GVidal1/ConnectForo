package com.microservicio.categorias.microservicio_categorias.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Categorías - ConnectForo")
                        .version("1.0")
                        .description("API REST para la gestión de categorías en el sistema ConnectForo. " +
                        "Permite crear, consultar, actualizar y eliminar categorías, así como " +
                        "realizar búsquedas por título, descripción, longitud y rango de fechas.")
                        );
    }
} 