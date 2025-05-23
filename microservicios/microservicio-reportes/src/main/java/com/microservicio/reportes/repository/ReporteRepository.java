package com.microservicio.reportes.repository;

import com.microservicio.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    
    List<Reporte> findByEstado(String estado);
    
    List<Reporte> findByIdUsuarioReportante(Long idUsuarioReportante);
    
    List<Reporte> findByIdUsuarioReportado(Long idUsuarioReportado);
    
    List<Reporte> findByIdForo(Long idForo);
    
    List<Reporte> findByIdCategoria(Long idCategoria);
    
    List<Reporte> findByTipoReporte(String tipoReporte);
} 