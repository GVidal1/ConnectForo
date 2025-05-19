package com.microservicio.rol.microservicio_rol.service;

import com.microservicio.rol.microservicio_rol.model.Rol;
import com.microservicio.rol.microservicio_rol.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Rol obtenerRolPorId(Long idRol) {
        return rolRepository.findById(idRol)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado un rol con ese ID."));
    }

    public Rol guardarRol(Rol nuevoRol) {
        return rolRepository.save(nuevoRol);
    }

    public Rol actualizarRol(Long idRol, Rol nuevaInfoRol) {
        Rol rolExistente = obtenerRolPorId(idRol);

        if (nuevaInfoRol.getTipoRol() != null) {
            rolExistente.setTipoRol(nuevaInfoRol.getTipoRol());
        }

        return rolRepository.save(rolExistente);
    }

    public String borrarRol(Long idRol) {
        Rol rolExistente = obtenerRolPorId(idRol);
        rolRepository.deleteById(rolExistente.getIdRol());
        return "Rol eliminado correctamente.";
    }
}
