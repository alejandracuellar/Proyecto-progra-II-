package model;

import java.time.LocalDateTime;

public class AutorizacionEspecial {

    private String cargoAutoriza;
    private String nombreAutorizador;
    private String documentoIdentidadAutorizador;
    private String firma;
    private LocalDateTime fechaAprobacion;

    public AutorizacionEspecial(String cargoAutoriza, String nombreAutorizador, String documentoIdentidadAutorizador, String firma, LocalDateTime fechaAprobacion) {
        this.cargoAutoriza = cargoAutoriza;
        this.nombreAutorizador = nombreAutorizador;
        this.documentoIdentidadAutorizador = documentoIdentidadAutorizador;
        this.firma = firma;
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getCargoAutoriza() {
        return cargoAutoriza;
    }

    public void setCargoAutoriza(String cargoAutoriza) {
        this.cargoAutoriza = cargoAutoriza;
    }

    public String getNombreAutorizador() {
        return nombreAutorizador;
    }

    public void setNombreAutorizador(String nombreAutorizador) {
        this.nombreAutorizador = nombreAutorizador;
    }

    public String getDocumentoIdentidadAutorizador() {
        return documentoIdentidadAutorizador;
    }

    public void setDocumentoIdentidadAutorizador(String documentoIdentidadAutorizador) {
        this.documentoIdentidadAutorizador = documentoIdentidadAutorizador;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    @Override
    public String toString() {
        return "AutorizacionEspecial{" +
                "cargoAutoriza='" + cargoAutoriza + '\'' +
                ", nombreAutorizador='" + nombreAutorizador + '\'' +
                ", documentoIdentidadAutorizador='" + documentoIdentidadAutorizador + '\'' +
                ", firma='" + firma + '\'' +
                ", fechaAprobacion=" + fechaAprobacion +
                '}';
    }
}
