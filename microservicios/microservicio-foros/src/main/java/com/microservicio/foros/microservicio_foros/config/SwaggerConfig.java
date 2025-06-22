package com.microservicio.foros.microservicio_foros.config;

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
                        .title("API de Foros - ConnectForo")
                        .version("1.0")
                        .description("API REST para la gestión de foros en el sistema ConnectForo. " +
                        "Permite crear, consultar, actualizar y eliminar foros, así como " +
                        "realizar búsquedas por título, contenido, fechas, usuario y categoría.")
                        );
    }
} 