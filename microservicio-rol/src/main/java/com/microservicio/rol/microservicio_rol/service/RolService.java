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

    // public List<Rol> obtenerRolesPorUsuario(Long idUsuario) {
    //     return rolRepository.findByIdUsuario(idUsuario);
    // }

    public Rol asignarRolAUsuario(Long idUsuario, Rol.TipoRol tipoRol) {
        Rol rol = new Rol();
        rol.setTipoRol(tipoRol);
        return rolRepository.save(rol);
    }
}
