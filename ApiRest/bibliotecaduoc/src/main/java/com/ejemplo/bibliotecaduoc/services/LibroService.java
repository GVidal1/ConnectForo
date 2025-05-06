package com.ejemplo.bibliotecaduoc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.bibliotecaduoc.model.Libro;
import com.ejemplo.bibliotecaduoc.repository.LibroRepository;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getLibros() {
        return libroRepository.obtenerLibros();
    }

    public Libro saveLibro(Libro libro) {
        return libroRepository.guardar(libro);
    }

    public Libro getLibroId(int id) {
        return libroRepository.buscarPorId(id);
    }

    public Libro updateLibro (Libro libro) {
        return libroRepository.actualizarLibro(libro);
    }

    public String deleteLibro (int id) {

        libroRepository.eliminar(id);
        return "Producto Eliminado";
    }

    public int totalLibrosV1() {
        return libroRepository.obtenerLibros().size();
    }

    public int totalLibrosV2() {
        return libroRepository.totalLibro();
    }
}
