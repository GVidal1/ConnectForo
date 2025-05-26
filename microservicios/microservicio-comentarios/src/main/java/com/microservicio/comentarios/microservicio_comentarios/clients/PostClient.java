package com.microservicio.comentarios.microservicio_comentarios.clients;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PostClient {
    
  private final WebClient webClient;

  //Metodo constructor 
  public PostClient(@Value("${post-service.url}") String postClientUrl) {
      this.webClient = WebClient.builder().baseUrl(postClientUrl).build();
  }

  //metodo para realizar la consunlta de getMapping("id") del microservicio de cliente
  @SuppressWarnings("unchecked")
  public Map<String, Object> obtenerPostPorId(Long id) {
      return this.webClient.get()
          .uri("/{id}", id)
          .retrieve()
          .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Publicación no encontrada")))
          .bodyToMono(Map.class).block();
          }
}
