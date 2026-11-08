package model;

import java.time.LocalDateTime;
import java.util.List;

public class Prestamo {
    private String codigoFormatoFisico;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaEjecucion;
    private LocalDateTime fechaDevolucionEsperada;
    private LocalDateTime fechaDevolucionReal;
    private String destinoUbicacion;
    private String estadoPrestamo;
    private String observaciones;
    private List<Recurso> equiposSolicitados;
    private Usuario usuarioSolicitante;
    private FuncionarioAudiovisuales funcionarioEntrega;
    private FuncionarioAudiovisuales funcionarioRecibe;
    private Docente docenteAval;

    public Prestamo(String codigoFormatoFisico, LocalDateTime fechaSolicitud, LocalDateTime fechaEjecucion,
                    LocalDateTime fechaDevolucionEsperada, LocalDateTime fechaDevolucionReal, String destinoUbicacion,
                    String estadoPrestamo, String observaciones, List<Recurso> equiposSolicitados, Usuario usuarioSolicitante,
                    FuncionarioAudiovisuales funcionarioEntrega, FuncionarioAudiovisuales funcionarioRecibe, Docente docenteAval) {
        this.codigoFormatoFisico = codigoFormatoFisico;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaEjecucion = fechaEjecucion;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.destinoUbicacion = destinoUbicacion;
        this.estadoPrestamo = estadoPrestamo;
        this.observaciones = observaciones;
        this.equiposSolicitados = equiposSolicitados;
        this.usuarioSolicitante = usuarioSolicitante;
        this.funcionarioEntrega = funcionarioEntrega;
        this.funcionarioRecibe = funcionarioRecibe;
        this.docenteAval = docenteAval;
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

    public String getDestinoUbicacion() {
        return destinoUbicacion;
    }

    public void setDestinoUbicacion(String destinoUbicacion) {
        this.destinoUbicacion = destinoUbicacion;
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

    public List<Recurso> getEquiposSolicitados() {
        return equiposSolicitados;
    }

    public void setEquiposSolicitados(List<Recurso> equiposSolicitados) {
        this.equiposSolicitados = equiposSolicitados;
    }

    public Usuario getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public FuncionarioAudiovisuales getFuncionarioEntrega() {
        return funcionarioEntrega;
    }

    public void setFuncionarioEntrega(FuncionarioAudiovisuales funcionarioEntrega) {
        this.funcionarioEntrega = funcionarioEntrega;
    }

    public FuncionarioAudiovisuales getFuncionarioRecibe() {
        return funcionarioRecibe;
    }

    public void setFuncionarioRecibe(FuncionarioAudiovisuales funcionarioRecibe) {
        this.funcionarioRecibe = funcionarioRecibe;
    }

    public Docente getDocenteAval() {
        return docenteAval;
    }

    public void setDocenteAval(Docente docenteAval) {
        this.docenteAval = docenteAval;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "codigoFormatoFisico='" + codigoFormatoFisico + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                ", fechaEjecucion=" + fechaEjecucion +
                ", fechaDevolucionEsperada=" + fechaDevolucionEsperada +
                ", fechaDevolucionReal=" + fechaDevolucionReal +
                ", destinoUbicacion='" + destinoUbicacion + '\'' +
                ", estadoPrestamo='" + estadoPrestamo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", equiposSolicitados=" + equiposSolicitados +
                ", usuarioSolicitante=" + usuarioSolicitante +
                ", funcionarioEntrega=" + funcionarioEntrega +
                ", funcionarioRecibe=" + funcionarioRecibe +
                ", docenteAval=" + docenteAval +
                '}';
    }
}