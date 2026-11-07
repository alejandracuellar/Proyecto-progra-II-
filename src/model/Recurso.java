package model;

public abstract class Recurso {
    private String codigoEquipo;
    private String descripcion;
    private String estado;
    private boolean requiereAutorizacionDocente;
    private Ubicacion ubicacionActual;

    public Recurso(String codigoEquipo, String descripcion, String estado, boolean requiereAutorizacionDocente, Ubicacion ubicacionActual) {
        this.codigoEquipo = codigoEquipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.requiereAutorizacionDocente = requiereAutorizacionDocente;
        this.ubicacionActual = ubicacionActual;
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

    public Ubicacion getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(Ubicacion ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "codigoEquipo='" + codigoEquipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", requiereAutorizacionDocente=" + requiereAutorizacionDocente +
                ", ubicacionActual=" + ubicacionActual +
                '}';
    }
}
