package com.ejemplo.bibliotecaduoc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ejemplo.bibliotecaduoc.model.Prestamo;

@Repository
public class PrestamoRepository {
    private List<Prestamo> listaPrestamos = new ArrayList<>();


    public PrestamoRepository() {
        listaPrestamos.add(new Prestamo(
            1,
            2,
            "20953728-1",
            "20-04-2025",
            "22-04-2025",
            3,
            0
        ));
    }

    public List<Prestamo> obtenerPrestamos() {
        return listaPrestamos;
    }

    public Prestamo buscarPorId(int id) {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.getIdPrestamo() == id) {
                return prestamo;
            }
        }
        return null;
    }

    public Prestamo guardPrestamo(Prestamo prestamo) {
        listaPrestamos.add(prestamo);
        return prestamo;
    }

    public Prestamo actualizarPrestamo(Prestamo prestamo){
        int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaPrestamos.size(); i++) {
            if (listaPrestamos.get(i).getIdPrestamo() == prestamo.getIdPrestamo()) {
                id = prestamo.getIdPrestamo();
                idPosicion = i;
            }
        }

        Prestamo prest = new Prestamo();
        prest.setIdPrestamo(id);
        prest.setIdLibro(prest.getIdLibro());
        prest.setRunSolicitante(prest.getRunSolicitante());
        prest.setFechaSolicitud(prest.getFechaSolicitud());
        prest.setFechaEntrega(prest.getFechaEntrega());
        prest.setCantidadDias(prest.getCantidadDias());
        prest.setMultas(prest.getMultas());


        listaPrestamos.set(idPosicion, prest);
        return prest;
    }
    
    public void eliminar(int id) {

        Prestamo prestamo = buscarPorId(id);

        if (prestamo != null) {
            listaPrestamos.remove(prestamo);
        }


        int idPosicion = 0;
        for (int i = 0; i < listaPrestamos.size(); i++){
            if ((listaPrestamos.get(i).getIdPrestamo() == id)) {
                idPosicion = i;
                break;
            }
        }

        if (idPosicion > 0) {
            listaPrestamos.remove(idPosicion);
        }

        listaPrestamos.removeIf(x -> x.getIdPrestamo() == id);
    }


}
