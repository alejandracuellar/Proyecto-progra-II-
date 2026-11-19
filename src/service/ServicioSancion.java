package service;

import model.Sancion;
import persistence.PersistenciaSancion;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServicioSancion {

    private ArrayList<Sancion> sanciones;
    private PersistenciaSancion persistencia;

    public ServicioSancion() {
        this.persistencia = new PersistenciaSancion();
        this.sanciones = persistencia.leer();

        if (this.sanciones == null) {
            this.sanciones = new ArrayList<>();
        }
    }

    public void agregarSancion(Sancion sancion) {
        if (sancion != null) {
            this.sanciones.add(sancion);
        }
    }

    public Sancion buscarPorId(String idSancion) {
        for (Sancion sancion : sanciones) {
            if (sancion.getIdSancion().equalsIgnoreCase(idSancion)) {
                return sancion;
            }
        }
        return null;
    }

    public ArrayList<Sancion> obtenerTodas() {
        return this.sanciones;
    }


    public void calcularFinSancion(String idSancion, int diasPenalizacion) {
        Sancion sancion = buscarPorId(idSancion);

        if (sancion != null && sancion.getFechaInicio() != null) {
            LocalDateTime nuevaFechaFin = sancion.getFechaInicio().plusDays(diasPenalizacion);
            sancion.setFechaFin(nuevaFechaFin);
        }
    }

    public void desactivarSancion(String idSancion) {
        Sancion sancion = buscarPorId(idSancion);
        if (sancion != null) {
            sancion.setActiva(false);
        }
    }


    public void guardarAlFinal() {
        persistencia.escribir(this.sanciones);
    }
}