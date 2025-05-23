package com.microservicio.soporte.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {
    private final WebClient webClient;
    private final String baseUrl;

    public UsuarioClient(WebClient.Builder webClientBuilder, @Value("${usuario.service.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }

    public Object getUsuarioById(Long id) {
        return webClient.get()
                .uri("/api/usuarios/{id}", id)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
} 