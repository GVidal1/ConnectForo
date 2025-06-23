package com.microservicio.registro.microservicio_registro.controller;

import com.microservicio.registro.microservicio_registro.service.RegistroService;
import com.microservicio.registro.microservicio_registro.dto.UsuarioDTO;
import com.microservicio.registro.microservicio_registro.dto.UsuarioRespuestaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registro")
@Tag(name = "Registro", description = "API para registro de nuevos usuarios en ConnectForo")
public class RegistroController {
    
    @Autowired
    private RegistroService registroService;
    
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = UsuarioRespuestaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Error al registrar: El correo ya está en uso"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Error al registrar: Error interno del servidor")))
    })
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDTO usuario) {
        try {
            UsuarioDTO nuevoRegistro = registroService.registrarUsuario(usuario);
            UsuarioRespuestaDTO respuesta = new UsuarioRespuestaDTO(
                nuevoRegistro.getIdRol(),
                nuevoRegistro.getNombreUsuario(),
                nuevoRegistro.getCorreo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al registrar: " + e.getMessage());
        }
    }
} 