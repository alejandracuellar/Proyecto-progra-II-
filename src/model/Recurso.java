package model;

import java.util.HashMap;
import java.util.Map;

public class Recurso {
    private String codigoEquipo;
    private String descripcion;
    private EstadoRecurso estadoRecurso;
    private boolean requiereAutorizacionDocente;
    private Ubicacion ubicacionActual;
    private String marca;
    private String numeroSerie;
    private String categoria;
    private String especificacionesAdicionales;

    public Recurso() {}

    public Recurso(String codigoEquipo, String descripcion, EstadoRecurso estadoRecurso, boolean requiereAutorizacionDocente,
                   Ubicacion ubicacionActual, String marca, String numeroSerie, String categoria, String especificacionesAdicionales) {
        this.codigoEquipo = codigoEquipo;
        this.descripcion = descripcion;
        this.estadoRecurso = estadoRecurso;
        this.requiereAutorizacionDocente = requiereAutorizacionDocente;
        this.ubicacionActual = ubicacionActual;
        this.marca = marca;
        this.numeroSerie = numeroSerie;
        this.categoria = categoria;
        this.especificacionesAdicionales = especificacionesAdicionales;
    }

    public boolean consultarDisponibilidad() {
                return this.estadoRecurso == EstadoRecurso.DISPONIBLE;
    }

    public Map<String, String> getDatosAutocompletar() {
        Map<String, String> datos = new HashMap<>();
        datos.put("codigoEquipo", this.codigoEquipo);
        datos.put("descripcion", this.descripcion);
        datos.put("marca", this.marca);
        datos.put("numeroSerie", this.numeroSerie);
        datos.put("categoria", this.categoria);
        datos.put("especificacionesAdicionales", this.especificacionesAdicionales);


        datos.put("estado", this.estadoRecurso != null ? this.estadoRecurso.name() : "");
        datos.put("requiereAutorizacionDocente", String.valueOf(this.requiereAutorizacionDocente));

        if (this.ubicacionActual != null) {
            datos.put("ubicacionActual", this.ubicacionActual.toString());
        }
        return datos;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoRecurso getEstadoRecurso() {
        return estadoRecurso;
    }

    public void setEstadoRecurso(EstadoRecurso estadoRecurso) {
        this.estadoRecurso = estadoRecurso;
    }

    public boolean isRequiereAutorizacionDocente() {
        return requiereAutorizacionDocente;
    }

    public void setRequiereAutorizacionDocente(boolean requiereAutorizacionDocente) {
        this.requiereAutorizacionDocente = requiereAutorizacionDocente;
    }

    public Ubicacion getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(Ubicacion ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEspecificacionesAdicionales() {
        return especificacionesAdicionales;
    }

    public void setEspecificacionesAdicionales(String especificacionesAdicionales) {
        this.especificacionesAdicionales = especificacionesAdicionales;
    }
}