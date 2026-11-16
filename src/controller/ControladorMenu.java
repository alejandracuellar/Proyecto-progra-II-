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

    public ControladorMenu() {
        this.servicioEstudiante = new ServicioEstudiante();
        this.servicioDocente = new ServicioDocente();
        this.servicioFuncionarioAudiovisuales = new ServicioFuncionarioAudiovisuales();
        this.servicioFuncionarioSolicitante = new ServicioFuncionarioSolicitante();
        this.servicioPrestamo = new ServicioPrestamo();
        this.servicioRecurso = new ServicioRecurso();
        this.servicioSancion = new ServicioSancion();
        this.servicioAuditorio = new ServicioAuditorio();
    }

    public void abrirVentanaPrestamos() {
        // Lógica para mostrar la interfaz de Préstamos
        // Ej: JFramePrestamos ventana = new JFramePrestamos(this.servicioPrestamo, ...);
        // ventana.setVisible(true);
    }

    public void abrirVentanaRecursos() {
        // Lógica para mostrar la interfaz de Recursos
    }

    public void abrirVentanaUsuarios() {
        // Lógica para mostrar la interfaz de Usuarios
    }


    public void cerrarAplicacion() {
        System.out.println("Iniciando proceso de guardado...");

        try {

            servicioEstudiante.guardarAlFinal();
            servicioDocente.guardarAlFinal();
            servicioFuncionarioAudiovisuales.guardarAlFinal();
            servicioFuncionarioSolicitante.guardarAlFinal();
            servicioPrestamo.guardarAlFinal();
            servicioRecurso.guardarAlFinal();
            servicioSancion.guardarAlFinal();
            servicioAuditorio.guardarAlFinal();

            System.out.println("Todos los datos se han guardado correctamente en los archivos JSON.");

            System.exit(0);

        } catch (Exception e) {
            System.err.println("Error al intentar guardar los datos antes de salir: " + e.getMessage());
        }
    }
}