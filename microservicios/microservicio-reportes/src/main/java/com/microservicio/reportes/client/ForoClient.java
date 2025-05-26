package com.microservicio.reportes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ForoClient {
    private final WebClient webClient;
    private final String baseUrl;

    public ForoClient(WebClient.Builder webClientBuilder, @Value("${foro.service.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }

    public Object getForoById(Long id) {
        return webClient.get()
                .uri("/api/foros/{id}", id)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
} 