package service;

import model.EstadoRecurso;
import model.Recurso;
import model.Ubicacion;
import persistence.PersistenciaInventario;

import java.util.ArrayList;

public class ServicioRecurso {

    private ArrayList<Recurso> recursos;
    private PersistenciaInventario persistencia;

    public ServicioRecurso() {
        this.persistencia = new PersistenciaInventario();
        this.recursos = persistencia.leer();

        if (this.recursos == null) {
            this.recursos = new ArrayList<>();
        }
    }

    public void agregarRecurso(Recurso recurso) {
        this.recursos.add(recurso);
    }

    public Recurso buscarPorCodigo(String codigoEquipo) {
        for (Recurso recurso : recursos) {
            if (recurso.getCodigoEquipo().equalsIgnoreCase(codigoEquipo)) {
                return recurso;
            }
        }
        return null;
    }

    public ArrayList<Recurso> obtenerTodos() {
        return this.recursos;
    }


    public boolean consultarDisponibilidad(String codigoEquipo) {
        Recurso recurso = buscarPorCodigo(codigoEquipo);

        if (recurso != null) {
            return recurso.getEstadoRecurso() == EstadoRecurso.DISPONIBLE;
        }
        return false;
    }

    public Ubicacion consultarUbicacionActual(String codigoEquipo) {
        Recurso recurso = buscarPorCodigo(codigoEquipo);

        if (recurso != null) {
            return recurso.getUbicacionActual();
        }
        return null;
    }

    public void cambiarEstadoRecurso(String codigoEquipo, EstadoRecurso nuevoEstado) {
        Recurso recurso = buscarPorCodigo(codigoEquipo);
        if (recurso != null) {
            recurso.setEstadoRecurso(nuevoEstado);
        }
    }


    public void guardarAlFinal() {
        persistencia.escribir(this.recursos);
    }
}