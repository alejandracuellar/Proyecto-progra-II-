package model;

import java.time.LocalDateTime;

public class Sancion {
    private String idSancion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
    private int numeroStrikes;
    private boolean activa;

    public Sancion(String idSancion, LocalDateTime fechaInicio, LocalDateTime fechaFin, String motivo, int numeroStrikes, boolean activa) {
        this.idSancion = idSancion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.motivo = motivo;
        this.numeroStrikes = numeroStrikes;
        this.activa = activa;
    }

    public String getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(String idSancion) {
        this.idSancion = idSancion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getNumeroStrikes() {
        return numeroStrikes;
    }

    public void setNumeroStrikes(int numeroStrikes) {
        this.numeroStrikes = numeroStrikes;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Sancion{" +
                "idSancion='" + idSancion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", motivo='" + motivo + '\'' +
                ", numeroStrikes=" + numeroStrikes +
                ", activa=" + activa +
                '}';
    }
}
