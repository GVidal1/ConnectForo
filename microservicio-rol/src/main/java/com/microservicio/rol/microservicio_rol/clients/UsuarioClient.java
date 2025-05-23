package com.microservicio.rol.microservicio_rol.clients;

import com.microservicio.rol.microservicio_rol.model.Usuarios; // Este modelo debe coincidir o adaptarse
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuarios-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Usuarios obtenerUsuarioPorId(Long idUsuario) {
        return webClient.get()
                .uri("/{idUsuario}", idUsuario)
                .retrieve()
                .bodyToMono(Usuarios.class)
                .block();
    }

    public List<Usuarios> obtenerTodosLosUsuarios() {
        Usuarios[] usuarios = webClient.get()
                .uri("")
                .retrieve()
                .bodyToMono(Usuarios[].class)
                .block();
        return Arrays.asList(usuarios);
    }
}

