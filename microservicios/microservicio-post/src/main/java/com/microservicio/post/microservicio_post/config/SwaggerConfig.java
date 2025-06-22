package com.microservicio.post.microservicio_post.config;

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
                        .title("API de Publicaciones - ConnectForo")
                        .version("1.0")
                        .description("API REST para la gestión de publicaciones en el sistema ConnectForo. " +
                        "Permite crear, consultar, actualizar y eliminar publicaciones, así como " +
                        "realizar búsquedas por título, contenido, fechas, usuario y foro.")
                        );
    }
} 