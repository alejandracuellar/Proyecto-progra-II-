package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Prestamo {
    private String codigoFormatoFisico;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaEjecucion;
    private LocalDateTime fechaDevolucionEsperada;
    private LocalDateTime fechaDevolucionReal;
    private String estadoPrestamo;
    private String observaciones;

    public Prestamo(String codigoFormatoFisico, LocalDateTime fechaSolicitud, LocalDateTime fechaEjecucion, LocalDateTime fechaDevolucionEsperada, LocalDateTime fechaDevolucionReal, String estadoPrestamo, String observaciones) {
        this.codigoFormatoFisico = codigoFormatoFisico;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaEjecucion = fechaEjecucion;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estadoPrestamo = estadoPrestamo;
        this.observaciones = observaciones;
    }

    public String getCodigoFormatoFisico() {
        return codigoFormatoFisico;
    }

    public void setCodigoFormatoFisico(String codigoFormatoFisico) {
        this.codigoFormatoFisico = codigoFormatoFisico;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public LocalDateTime getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDateTime fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDateTime getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDateTime fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(String estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "codigoFormatoFisico='" + codigoFormatoFisico + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                ", fechaEjecucion=" + fechaEjecucion +
                ", fechaDevolucionEsperada=" + fechaDevolucionEsperada +
                ", fechaDevolucionReal=" + fechaDevolucionReal +
                ", estadoPrestamo='" + estadoPrestamo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
