package com.microservicio.usuarios.microservicio_usuarios.config;

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
                        .title("API de Usuarios - ConnectForo")
                        .version("1.0")
                        .description("API REST para la gestión de usuarios en el sistema ConnectForo. " +
                        "Permite crear, consultar, actualizar y eliminar usuarios, así como " +
                        "gestionar autenticación y recuperación de contraseñas.")
                        );
    }
} 