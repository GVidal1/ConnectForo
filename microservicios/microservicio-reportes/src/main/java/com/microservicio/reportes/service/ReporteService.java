package com.microservicio.reportes.service;

import com.microservicio.reportes.exception.ResourceNotFoundException;
import com.microservicio.reportes.feign.CategoriaClient;
import com.microservicio.reportes.feign.ForoClient;
import com.microservicio.reportes.model.Reporte;
import com.microservicio.reportes.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;
    
    @Autowired
    private ForoClient foroClient;
    
    @Autowired
    private CategoriaClient categoriaClient;

    @Transactional(readOnly = true)
    public List<Reporte> findAll() {
        return reporteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reporte findById(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte", "id", id));
    }

    @Transactional
    public Reporte save(Reporte reporte) {
        // Validar foro si existe
        if (reporte.getIdForo() != null) {
            try {
                foroClient.getForoById(reporte.getIdForo());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Foro", "id", reporte.getIdForo());
            }
        }
        
        // Validar categoría si existe
        if (reporte.getIdCategoria() != null) {
            try {
                categoriaClient.getCategoriaById(reporte.getIdCategoria());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Categoria", "id", reporte.getIdCategoria());
            }
        }
        
        return reporteRepository.save(reporte);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!reporteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte", "id", id);
        }
        reporteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Reporte> findByEstado(String estado) {
        return reporteRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Reporte> findByIdUsuarioReportante(Long idUsuarioReportante) {
        return reporteRepository.findByIdUsuarioReportante(idUsuarioReportante);
    }

    @Transactional(readOnly = true)
    public List<Reporte> findByIdUsuarioReportado(Long idUsuarioReportado) {
        return reporteRepository.findByIdUsuarioReportado(idUsuarioReportado);
    }

    @Transactional(readOnly = true)
    public List<Reporte> findByIdForo(Long idForo) {
        return reporteRepository.findByIdForo(idForo);
    }

    @Transactional(readOnly = true)
    public List<Reporte> findByIdCategoria(Long idCategoria) {
        return reporteRepository.findByIdCategoria(idCategoria);
    }

    @Transactional(readOnly = true)
    public List<Reporte> findByTipoReporte(String tipoReporte) {
        return reporteRepository.findByTipoReporte(tipoReporte);
    }

    @Transactional
    public Reporte updateEstado(Long id, String nuevoEstado) {
        Reporte reporte = findById(id);
        reporte.setEstado(nuevoEstado);
        return reporteRepository.save(reporte);
    }
} 