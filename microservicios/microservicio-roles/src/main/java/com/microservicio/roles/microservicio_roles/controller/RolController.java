package com.microservicio.roles.microservicio_roles.controller;

import com.microservicio.roles.microservicio_roles.dto.UsuarioDTO;
import com.microservicio.roles.microservicio_roles.model.Rol;
import com.microservicio.roles.microservicio_roles.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "API para gestión de roles en ConnectForo")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Listar todos los roles", description = "Retorna una lista de todos los roles disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "204", description = "No hay roles disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolService.listarRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Buscar rol por ID", description = "Obtiene la información de un rol específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Rol no encontrado")))
    })
    @GetMapping("/{idRol}")
    public ResponseEntity<?> obtenerRolPorId(@PathVariable Long idRol) {
        try {
            Rol rol = rolService.obtenerRolPorId(idRol);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar usuarios por rol", description = "Obtiene una lista de usuarios que pertenecen a un rol específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios con ese rol"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{idRol}/usuarios")
    public ResponseEntity<?> obtenerUsuariosPorRol(@PathVariable Long idRol) {
        try {
            List<UsuarioDTO> usuarios = rolService.obtenerUsuariosPorRol(idRol);
            if (usuarios == null || usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener usuarios por rol: " + e.getMessage());
        }
    }

    @Operation(summary = "Crear nuevo rol", description = "Crea un nuevo rol en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rol creado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "400", description = "Error al crear el rol",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Error al crear el rol: El tipo de rol ya existe")))
    })
    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody @Valid Rol nuevoRol) {
        try {
            Rol rolGuardado = rolService.guardarRol(nuevoRol);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el rol: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar rol existente", description = "Actualiza la información de un rol existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "400", description = "Error al actualizar el rol",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Error al actualizar el rol: El tipo de rol ya existe"))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @PutMapping("/{idRol}")
    public ResponseEntity<?> actualizarRol(
        @PathVariable Long idRol, 
        @RequestBody @Valid Rol nuevaInfo) {
        try {
            Rol rolActualizado = rolService.actualizarRol(idRol, nuevaInfo);
            return ResponseEntity.ok(rolActualizado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error al actualizar el rol: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Rol no encontrado")))
    })
    @DeleteMapping("/{idRol}")
    public ResponseEntity<?> borrarRol(@PathVariable Long idRol) {
        try {
            String mensaje = rolService.borrarRol(idRol);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
} 