package com.microservicio.roles.microservicio_roles.config;

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
                        .title("API de Roles - ConnectForo")
                        .version("1.0")
                        .description("API REST para la gestión de roles en el sistema ConnectForo. " +
                        "Permite crear, consultar, actualizar y eliminar roles, así como " +
                        "consultar usuarios asociados a cada rol.")
                        );
    }
}