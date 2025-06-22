package com.microservicio.registro.microservicio_registro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSwagger {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Registro - ConnectForo")
                        .version("1.0")
                        .description("API REST para el registro de nuevos usuarios en el sistema ConnectForo. " +
                        "Permite crear nuevas cuentas de usuario con validación de datos.")
                        );
    }
}