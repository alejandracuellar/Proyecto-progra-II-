package model;

import java.util.Map;

public abstract class Usuario {
    private String id;
    private String documentoIdentidad;
    private String nombreCompleto;
    private String telefono;
    private String escuelaDependencia;
    private String firmaDigital;

    public Usuario() {}

    public Usuario(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependencia, String firmaDigital) {
        this.id = id;
        this.documentoIdentidad = documentoIdentidad;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.escuelaDependencia = escuelaDependencia;
        this.firmaDigital = firmaDigital;
    }

    public abstract Map<String, String> getDatosAutocompletar();
    public abstract boolean puedeSolicitarPrestamo();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEscuelaDependencia() {
        return escuelaDependencia;
    }

    public void setEscuelaDependencia(String escuelaDependencia) {
        this.escuelaDependencia = escuelaDependencia;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }
}
