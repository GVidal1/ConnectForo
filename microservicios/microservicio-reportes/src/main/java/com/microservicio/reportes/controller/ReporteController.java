package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Reporte;
import com.microservicio.reportes.service.ReporteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> getAllReportes() {
        List<Reporte> reportes = reporteService.findAll();
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getReporteById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Reporte> createReporte(@Valid @RequestBody Reporte reporte) {
        Reporte savedReporte = reporteService.save(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> updateReporte(@PathVariable Long id, @Valid @RequestBody Reporte reporte) {
        reporte.setId(id);
        return ResponseEntity.ok(reporteService.save(reporte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reporte>> getReportesByEstado(@PathVariable String estado) {
        List<Reporte> reportes = reporteService.findByEstado(estado);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/usuario-reportante/{id}")
    public ResponseEntity<List<Reporte>> getReportesByUsuarioReportante(@PathVariable Long id) {
        List<Reporte> reportes = reporteService.findByIdUsuarioReportante(id);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/usuario-reportado/{id}")
    public ResponseEntity<List<Reporte>> getReportesByUsuarioReportado(@PathVariable Long id) {
        List<Reporte> reportes = reporteService.findByIdUsuarioReportado(id);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/foro/{id}")
    public ResponseEntity<List<Reporte>> getReportesByForo(@PathVariable Long id) {
        List<Reporte> reportes = reporteService.findByIdForo(id);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Reporte>> getReportesByCategoria(@PathVariable Long id) {
        List<Reporte> reportes = reporteService.findByIdCategoria(id);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Reporte>> getReportesByTipo(@PathVariable String tipo) {
        List<Reporte> reportes = reporteService.findByTipoReporte(tipo);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
} 