package com.microservicio.usuarios.microservicio_usuarios.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicio.usuarios.microservicio_usuarios.client.RolClient;
import com.microservicio.usuarios.microservicio_usuarios.model.Usuarios;
import com.microservicio.usuarios.microservicio_usuarios.repository.UsuarioRepository;
import com.microservicio.usuarios.microservicio_usuarios.dto.CambiarPasswordDTO;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolClient rolClient;

    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado un usuario con ese ID."));
    }

    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado un usuario con ese correo."));
    }

    public List<Usuarios> obtenerUsuariosPorRol(Long idRol) {
        Map<String, Object> verificarRol = rolClient.obtenerRolPorId(idRol);
        if (verificarRol == null || verificarRol.isEmpty()) {
            throw new RuntimeException("El rol con ID " + idRol + " no existe.");
        }
        return usuarioRepository.findByIdRol(idRol);
    }

    public String borrarUsuario(Long id) {
        Usuarios usuarioAct = obtenerUsuarioPorId(id);
        usuarioRepository.deleteById(usuarioAct.getId());
        return "Se ha eliminado el usuario correctamente.";
    }

    public Usuarios guardarUsuario(Usuarios nuevoUsuario) {
        Map<String, Object> verificarRol = rolClient.obtenerRolPorId(nuevoUsuario.getIdRol());
        if (verificarRol == null || verificarRol.isEmpty()) {
            throw new RuntimeException("El id de la publicación no se ha encontrado. No se puede crear un comentario.");
        }
        
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuarios actualizarUsuario(Long idUsuario, Usuarios nuevaInfo) {
        Usuarios usuarioActual = obtenerUsuarioPorId(idUsuario);

        if (nuevaInfo.getNombreUsuario() != null) {
            usuarioActual.setNombreUsuario(nuevaInfo.getNombreUsuario());
        }

        if (nuevaInfo.getPassword() != null) {
            usuarioActual.setPassword(passwordEncoder.encode(nuevaInfo.getPassword()));
        }

        if (nuevaInfo.getCorreo() != null) {
            usuarioActual.setCorreo(nuevaInfo.getCorreo());
        }

        if (nuevaInfo.getIdRol() != null) {
            Map<String, Object> verificarRol = rolClient.obtenerRolPorId(nuevaInfo.getIdRol());
            if (verificarRol == null || verificarRol.isEmpty()) {
                throw new RuntimeException("El rol con ID " + nuevaInfo.getIdRol() + " no existe. No se puede actualizar el usuario.");
            }
            usuarioActual.setIdRol(nuevaInfo.getIdRol());
        }

        return guardarUsuario(usuarioActual);
    }

    public List<Usuarios> buscarUsuarios(String nombre, String correo) {
        if (nombre != null && correo != null) {
            return usuarioRepository.findByNombreUsuarioContainingOrCorreoContaining(nombre, correo);
        } else if (nombre != null) {
            return usuarioRepository.findByNombreUsuarioContaining(nombre);
        } else if (correo != null) {
            return usuarioRepository.findByCorreoContaining(correo);
        }
        return usuarioRepository.findAll();
    }

    public Usuarios cambiarPassword(Long idUsuario, CambiarPasswordDTO passwordDTO) {
        Usuarios usuario = obtenerUsuarioPorId(idUsuario);
        
        if (!passwordEncoder.matches(passwordDTO.getPasswordActual(), usuario.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }
        
        if (!passwordDTO.getNuevaPassword().equals(passwordDTO.getConfirmarPassword())) {
            throw new RuntimeException("Las contraseñas nuevas no coinciden");
        }
        
        usuario.setPassword(passwordEncoder.encode(passwordDTO.getNuevaPassword()));
        return usuarioRepository.save(usuario);
    }

    public Map<String, Object> obtenerEstadisticasUsuario(Long idUsuario) {
        Usuarios usuario = obtenerUsuarioPorId(idUsuario);
        Map<String, Object> estadisticas = new HashMap<>();
        
        estadisticas.put("id", usuario.getId());
        estadisticas.put("nombreUsuario", usuario.getNombreUsuario());
        estadisticas.put("correo", usuario.getCorreo());
        estadisticas.put("fechaCreacion", usuario.getFechaCreacion());
        estadisticas.put("rol", rolClient.obtenerRolPorId(usuario.getIdRol()));
        
        return estadisticas;
    }

    public void solicitarRecuperacionPassword(String correo) {
        Usuarios usuario = obtenerUsuarioPorCorreo(correo);
        // verificamos que el usuario existe
        if (usuario == null) {
            throw new RuntimeException("No se encontró ningún usuario con ese correo");
        }
    }
} 