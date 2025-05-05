package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Reporte;
import com.microservicio.reportes.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> getAllReportes() {
        return ResponseEntity.ok(reporteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getReporteById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Reporte> createReporte(@Valid @RequestBody Reporte reporte) {
        Reporte savedReporte = reporteService.save(reporte);
        return new ResponseEntity<>(savedReporte, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> updateReporte(@PathVariable Long id, @Valid @RequestBody Reporte reporte) {
        reporte.setId(id);
        return ResponseEntity.ok(reporteService.save(reporte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reporte>> getReportesByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reporteService.findByEstado(estado));
    }

    @GetMapping("/usuario-reportante/{id}")
    public ResponseEntity<List<Reporte>> getReportesByUsuarioReportante(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.findByIdUsuarioReportante(id));
    }

    @GetMapping("/usuario-reportado/{id}")
    public ResponseEntity<List<Reporte>> getReportesByUsuarioReportado(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.findByIdUsuarioReportado(id));
    }

    @GetMapping("/foro/{id}")
    public ResponseEntity<List<Reporte>> getReportesByForo(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.findByIdForo(id));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Reporte>> getReportesByCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.findByIdCategoria(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Reporte>> getReportesByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(reporteService.findByTipoReporte(tipo));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Reporte> updateEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(reporteService.updateEstado(id, nuevoEstado));
    }
} 