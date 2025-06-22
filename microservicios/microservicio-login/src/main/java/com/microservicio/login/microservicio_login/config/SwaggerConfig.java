package com.microservicio.login.microservicio_login.config;

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
                        .title("API de Login - ConnectForo")
                        .version("1.0")
                        .description("API REST para la autenticación y gestión de sesiones en el sistema ConnectForo. " +
                        "Permite iniciar sesión y recuperar contraseñas de usuarios.")
                        );
    }
}