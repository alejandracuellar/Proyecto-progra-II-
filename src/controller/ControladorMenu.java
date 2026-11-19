package controller;

import service.*;

public class ControladorMenu {

    private ServicioEstudiante servicioEstudiante;
    private ServicioDocente servicioDocente;
    private ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales;
    private ServicioFuncionarioSolicitante servicioFuncionarioSolicitante;
    private ServicioPrestamo servicioPrestamo;
    private ServicioRecurso servicioRecurso;
    private ServicioSancion servicioSancion;
    private ServicioAuditorio servicioAuditorio;
    private ServicioUbicacion servicioUbicacion;

    public ControladorMenu() {
        this.servicioEstudiante = new ServicioEstudiante();
        this.servicioDocente = new ServicioDocente();
        this.servicioFuncionarioAudiovisuales = new ServicioFuncionarioAudiovisuales();
        this.servicioFuncionarioSolicitante = new ServicioFuncionarioSolicitante();
        this.servicioPrestamo = new ServicioPrestamo();
        this.servicioRecurso = new ServicioRecurso();
        this.servicioSancion = new ServicioSancion();
        this.servicioAuditorio = new ServicioAuditorio();
        this.servicioUbicacion = new ServicioUbicacion();
    }

    public ControladorLogin getControladorLogin() {
        return new ControladorLogin(this.servicioFuncionarioAudiovisuales);
    }

    public ControladorPrestamo getControladorPrestamo() {
        return new ControladorPrestamo(
                this.servicioPrestamo,
                this.servicioRecurso,
                this.servicioEstudiante,
                this.servicioDocente,
                this.servicioFuncionarioSolicitante,
                this.servicioFuncionarioAudiovisuales,
                this.servicioSancion
        );
    }

    public ControladorRecurso getControladorRecurso() {
        return new ControladorRecurso(
                this.servicioRecurso,
                this.servicioUbicacion
        );
    }

    public ControladorAuditorio getControladorAuditorio() {
        return new ControladorAuditorio(this.servicioAuditorio);
    }

    public ControladorUsuario getControladorUsuario() {
        return new ControladorUsuario(
                this.servicioEstudiante,
                this.servicioDocente,
                this.servicioFuncionarioSolicitante,
                this.servicioFuncionarioAudiovisuales
        );
    }

    public ControladorSancion getControladorSancion() {
        return new ControladorSancion(
                this.servicioSancion,
                this.servicioEstudiante
        );
    }

    public void cerrarAplicacion() {
        try {
            servicioEstudiante.guardarAlFinal();
            servicioDocente.guardarAlFinal();
            servicioFuncionarioAudiovisuales.guardarAlFinal();
            servicioFuncionarioSolicitante.guardarAlFinal();
            servicioPrestamo.guardarAlFinal();
            servicioRecurso.guardarAlFinal();
            servicioSancion.guardarAlFinal();
            servicioAuditorio.guardarAlFinal();
            // servicioUbicacion.guardarAlFinal(); // Descomentar si tiene persistencia

            System.exit(0);
        } catch (Exception e) {
            System.err.println("Error al intentar guardar los datos antes de salir: " + e.getMessage());
        }
    }
}