package model;

public abstract class Recurso {
    private String codigoEquipo;
    private String descripcion;
    private String estado;
    private boolean requiereAutorizacionDocente;

    public Recurso(String codigoEquipo, String descripcion, String estado, boolean requiereAutorizacionDocente) {
        this.codigoEquipo = codigoEquipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.requiereAutorizacionDocente = requiereAutorizacionDocente;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isRequiereAutorizacionDocente() {
        return requiereAutorizacionDocente;
    }

    public void setRequiereAutorizacionDocente(boolean requiereAutorizacionDocente) {
        this.requiereAutorizacionDocente = requiereAutorizacionDocente;
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "codigoEquipo='" + codigoEquipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", requiereAutorizacionDocente=" + requiereAutorizacionDocente +
                '}';
    }
}
