package com.microservicio.usuarios.microservicio_usuarios.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RolClient {
    private final WebClient webClient;

    public RolClient(@Value("${rol-service.url}") String rolServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(rolServiceUrl).build();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerRolPorId(Long id) {
        return this.webClient.get()
            .uri("/{idRol}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Rol no encontrado")))
            .bodyToMono(Map.class).block();
    }
} 