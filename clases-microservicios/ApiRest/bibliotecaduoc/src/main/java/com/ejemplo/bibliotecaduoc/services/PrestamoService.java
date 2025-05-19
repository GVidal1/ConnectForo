package com.ejemplo.bibliotecaduoc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.bibliotecaduoc.model.Prestamo;
import com.ejemplo.bibliotecaduoc.repository.PrestamoRepository;

@Service
public class PrestamoService {
    @Autowired
    private PrestamoRepository prestamosRepository;

    public List<Prestamo> getPrestamos() {
        return prestamosRepository.obtenerPrestamos();
    }

    public Prestamo savePrestamo(Prestamo prestamo) {
        return prestamosRepository.guardPrestamo(prestamo);
    }

    public Prestamo getPrestamoId(int id) {
        return prestamosRepository.buscarPorId(id);
    }

    public Prestamo updatePrestamo (Prestamo prestamo) {
        return prestamosRepository.actualizarPrestamo(prestamo);
    }

    public String deletePrestamo (int id) {

        prestamosRepository.eliminar(id);
        return "Prestamo Eliminado";
    }
}
