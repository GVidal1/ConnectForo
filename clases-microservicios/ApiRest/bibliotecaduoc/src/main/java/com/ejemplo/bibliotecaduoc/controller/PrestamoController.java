package com.ejemplo.bibliotecaduoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.bibliotecaduoc.model.Prestamo;
import com.ejemplo.bibliotecaduoc.services.PrestamoService;

@RestController
@RequestMapping("api/v1/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoService.getPrestamos();
    }

    @PostMapping
    public Prestamo agregarPrestamo (@RequestBody Prestamo prestamo) {
        return prestamoService.savePrestamo(prestamo);
    }

    @GetMapping("{id}")
    public Prestamo buscarPrestamo (@PathVariable int id) {
        return prestamoService.getPrestamoId(id);
    }

    @PutMapping("{id}")
    public Prestamo actualizarPrestamo(@PathVariable int id, @RequestBody Prestamo prestamo) {
        return prestamoService.updatePrestamo(prestamo);
    }

    @DeleteMapping("{id}")
    public String eliminarPrestamo(@PathVariable int id) {
        return prestamoService.deletePrestamo(id);
    }
}
