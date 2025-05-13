package com.ejemplo.bibliotecaduoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {
    private int idPrestamo;
    private int idLibro;
    private String runSolicitante;
    private String fechaSolicitud;
    private String fechaEntrega;
    private int cantidadDias;
    private int multas;
}
