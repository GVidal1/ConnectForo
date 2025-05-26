package com.example.USER.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.USER.client.RolClient;
import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;

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

    return usuarioRepository.save(usuarioActual);
}

}
