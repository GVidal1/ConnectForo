package com.microservicio.reportes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CategoriaClient {
    private final WebClient webClient;
    private final String baseUrl;

    public CategoriaClient(WebClient.Builder webClientBuilder, @Value("${categoria.service.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }

    public Object getCategoriaById(Long id) {
        return webClient.get()
                .uri("/api/categorias/{id}", id)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
} 