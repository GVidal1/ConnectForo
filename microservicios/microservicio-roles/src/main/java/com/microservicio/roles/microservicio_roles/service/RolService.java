package com.microservicio.roles.microservicio_roles.service;

import com.microservicio.roles.microservicio_roles.model.Rol;
import com.microservicio.roles.microservicio_roles.repository.RolRepository;
import com.microservicio.roles.microservicio_roles.clients.UsuarioClient;
import com.microservicio.roles.microservicio_roles.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Rol obtenerRolPorId(Long idRol) {
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + idRol));
    }

    public Rol guardarRol(Rol nuevoRol) {
        return rolRepository.save(nuevoRol);
    }

    public Rol actualizarRol(Long idRol, Rol nuevaInfo) {
        Rol rolActual = obtenerRolPorId(idRol);
        rolActual.setTipoRol(nuevaInfo.getTipoRol());
        return rolRepository.save(rolActual);
    }

    public String borrarRol(Long idRol) {
        rolRepository.deleteById(idRol);
        return "Rol eliminado correctamente";
    }

    public List<UsuarioDTO> obtenerUsuariosPorRol(Long idRol) {
        // Valida que el rol exista
        this.obtenerRolPorId(idRol);
        List<UsuarioDTO> usuarios = usuarioClient.obtenerUsuariosPorRol(idRol);
        return usuarios;
    }
} 