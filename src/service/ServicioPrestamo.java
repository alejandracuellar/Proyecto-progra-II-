package service;

import model.*;
import persistence.PersistenciaJson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioPrestamo {

    private ArrayList<Prestamo> prestamos;
    private final PersistenciaJson persistencia;
    private final ServicioRecurso servicioRecurso;
    private final ServicioAuditorio servicioAuditorio;
    private final ServicioEstudiante servicioEstudiante;
    private final ServicioDocente servicioDocente;
    private final ServicioFuncionarioSolicitante servicioFuncionarioSolicitante;
    private final ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales;
    private final ServicioSancion servicioSancion;

    public ServicioPrestamo(PersistenciaJson persistencia, ServicioRecurso servicioRecurso,
                            ServicioAuditorio servicioAuditorio, ServicioEstudiante servicioEstudiante,
                            ServicioDocente servicioDocente, ServicioFuncionarioSolicitante servicioFuncionarioSolicitante,
                            ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales, ServicioSancion servicioSancion) {
        this.persistencia = persistencia;
        this.servicioRecurso = servicioRecurso;
        this.servicioAuditorio = servicioAuditorio;
        this.servicioEstudiante = servicioEstudiante;
        this.servicioDocente = servicioDocente;
        this.servicioFuncionarioSolicitante = servicioFuncionarioSolicitante;
        this.servicioFuncionarioAudiovisuales = servicioFuncionarioAudiovisuales;
        this.servicioSancion = servicioSancion;
        this.prestamos = persistencia.leer();
    }

    public boolean registrarSolicitudPrestamo(String codigoFormato, String docUsuario, List<String> codigosEquipos,
                                              String nombreAuditorio, String docDocenteAval,
                                              String idFuncionarioEntrega, String destino) throws Exception {

        // 1. Buscar el usuario solicitante entre las tres categorías
        Usuario solicitante = buscarUsuarioEnCualquierRol(docUsuario);

        // Validar si el usuario puede realizar solicitudes (strike/sanción)
        if (!solicitante.puedeSolicitarPrestamo()) {
            throw new Exception("El usuario está SANCIONADO por acumulación de 3 strikes.");
        }

        // 2. Restricción de auditorios
        if (nombreAuditorio != null && !nombreAuditorio.trim().isEmpty()) {
            if (solicitante instanceof Estudiante) {
                throw new Exception("Un estudiante no tiene permitido reservar auditorios. Debe solicitarlo un Docente o Funcionario.");
            }
            servicioAuditorio.buscarPorNombre(nombreAuditorio);
        }

        // 3. Validar equipos y aval docente
        List<Recurso> equiposPrestamo = new ArrayList<>();
        Docente docenteAvalObj = null;

        if (codigosEquipos != null && !codigosEquipos.isEmpty()) {
            for (String cod : codigosEquipos) {
                Recurso equipo = servicioRecurso.buscarPorCodigo(cod);

                if (!equipo.consultarDisponibilidad()) {
                    throw new Exception("El equipo " + cod + " no está disponible. Estado: " + equipo.getEstado());
                }

                if (equipo.isRequiereAutorizacionDocente()) {
                    if (docDocenteAval == null || docDocenteAval.trim().isEmpty()) {
                        throw new Exception("El equipo " + cod + " requiere el documento del Docente aval.");
                    }
                    docenteAvalObj = servicioDocente.buscarPorDocumento(docDocenteAval);
                }

                equipo.setEstado("PRESTADO");
                equiposPrestamo.add(equipo);
            }
        }

        FuncionarioAudiovisuales funcEntrega = servicioFuncionarioAudiovisuales.buscarPorId(idFuncionarioEntrega);

        LocalDateTime ahora = LocalDateTime.now();
        Prestamo nuevoPrestamo = new Prestamo(
                codigoFormato,
                ahora,
                ahora,
                ahora.plusHours(4),
                null,
                destino,
                "ACTIVO",
                "Sin observaciones",
                equiposPrestamo,
                solicitante,
                funcEntrega,
                null,
                docenteAvalObj
        );

        this.prestamos.add(nuevoPrestamo);
        persistencia.escribir(this.prestamos);
        servicioRecurso.guardarInventario();
        return true;
    }

    public void registrarDevolucion(String codigoFormato, String idFuncionarioRecibe,
                                    String observaciones, LocalDateTime fechaReal) throws Exception {
        Prestamo prestamoEncontrado = null;
        for (Prestamo p : prestamos) {
            if (p.getCodigoFormatoFisico().equalsIgnoreCase(codigoFormato) && "ACTIVO".equalsIgnoreCase(p.getEstadoPrestamo())) {
                prestamoEncontrado = p;
                break;
            }
        }

        if (prestamoEncontrado == null) {
            throw new Exception("No existe un préstamo activo con formato: " + codigoFormato);
        }

        FuncionarioAudiovisuales funcRecibe = servicioFuncionarioAudiovisuales.buscarPorId(idFuncionarioRecibe);
        prestamoEncontrado.setFechaDevolucionReal(fechaReal);
        prestamoEncontrado.setFuncionarioRecibe(funcRecibe);
        prestamoEncontrado.setObservaciones(observaciones);

        if (fechaReal.isAfter(prestamoEncontrado.getFechaDevolucionEsperada())) {
            prestamoEncontrado.setEstadoPrestamo("CON_RETRASO");
            if (prestamoEncontrado.getUsuarioSolicitante() instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) prestamoEncontrado.getUsuarioSolicitante();
                servicioSancion.aplicarStrikeYSancionSiAplica(estudiante, fechaReal);
            }
        } else {
            prestamoEncontrado.setEstadoPrestamo("DEVUELTO");
        }

        if (prestamoEncontrado.getEquiposSolicitados() != null) {
            for (Recurso equipo : prestamoEncontrado.getEquiposSolicitados()) {
                equipo.setEstado("DISPONIBLE");
            }
        }

        persistencia.escribir(this.prestamos);
        servicioRecurso.guardarInventario();
    }

    private Usuario buscarUsuarioEnCualquierRol(String documento) throws Exception {
        try {
            return servicioEstudiante.buscarPorDocumento(documento);
        } catch (Exception e1) {
            try {
                return servicioDocente.buscarPorDocumento(documento);
            } catch (Exception e2) {
                return servicioFuncionarioSolicitante.buscarPorDocumento(documento);
            }
        }
    }

    public ArrayList<Prestamo> getPrestamos() { return prestamos; }
}