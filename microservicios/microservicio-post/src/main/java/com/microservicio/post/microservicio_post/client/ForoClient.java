package com.microservicio.post.microservicio_post.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ForoClient {
    
  private final WebClient webClient;

  //Metodo constructor 
  public ForoClient(@Value("${foros-service.url}") String forosClientUrl) {
      this.webClient = WebClient.builder().baseUrl(forosClientUrl).build();
  }

  //metodo para realizar la consunlta de getMapping("id") del microservicio de cliente
  @SuppressWarnings("unchecked")
  public Map<String, Object> obtenerForoPorId(Long id) {
      return this.webClient.get()
          .uri("/{id}", id)
          .retrieve()
          .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Foro no encontrado")))
          .bodyToMono(Map.class).block();
          }
}
