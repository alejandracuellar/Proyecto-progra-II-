package controller;

import model.*;
import service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControladorPrestamo {

    private ServicioPrestamo servicioPrestamo;
    private ServicioRecurso servicioRecurso;
    private ServicioEstudiante servicioEstudiante;
    private ServicioDocente servicioDocente;
    private ServicioFuncionarioSolicitante servicioFuncionarioSolicitante;
    private ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales;
    private ServicioSancion servicioSancion;

    public ControladorPrestamo(ServicioPrestamo servicioPrestamo, ServicioRecurso servicioRecurso,
                               ServicioEstudiante servicioEstudiante, ServicioDocente servicioDocente,
                               ServicioFuncionarioSolicitante servicioFuncionarioSolicitante,
                               ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales,
                               ServicioSancion servicioSancion) {
        this.servicioPrestamo = servicioPrestamo;
        this.servicioRecurso = servicioRecurso;
        this.servicioEstudiante = servicioEstudiante;
        this.servicioDocente = servicioDocente;
        this.servicioFuncionarioSolicitante = servicioFuncionarioSolicitante;
        this.servicioFuncionarioAudiovisuales = servicioFuncionarioAudiovisuales;
        this.servicioSancion = servicioSancion;
    }


    public Map<String, String> buscarDatosSolicitante(String tipoSolicitante, String codigoSolicitante) {
        if (tipoSolicitante == null || codigoSolicitante == null || codigoSolicitante.trim().isEmpty()) {
            return Map.of();
        }

        switch (tipoSolicitante.toUpperCase()) {
            case "ESTUDIANTE":
                return servicioEstudiante.getDatosAutocompletar(codigoSolicitante);
            case "DOCENTE":
                return servicioDocente.getDatosAutocompletar(codigoSolicitante);
            case "FUNCIONARIO":
                return servicioFuncionarioSolicitante.getDatosAutocompletar(codigoSolicitante);
            default:
                return Map.of();
        }
    }


    public String registrarPrestamo(String codigoFormatoFisico, String tipoSolicitante, String codigoSolicitante,
                                    List<String> codigosEquipos, String destinoUbicacion, LocalDateTime fechaEjecucion,
                                    LocalDateTime fechaDevolucionEsperada, String codigoDocenteResponsable,
                                    String idFuncionarioEntrega, String observaciones) {

        if (codigoFormatoFisico == null || codigoFormatoFisico.trim().isEmpty()) {
            return "Error: El código del formato físico es obligatorio.";
        }
        if (tipoSolicitante == null || tipoSolicitante.trim().isEmpty()) {
            return "Error: Debe seleccionar el tipo de solicitante.";
        }
        if (codigoSolicitante == null || codigoSolicitante.trim().isEmpty()) {
            return "Error: Debe ingresar el código del solicitante.";
        }
        if (codigosEquipos == null || codigosEquipos.isEmpty()) {
            return "Error: Debe agregar al menos un recurso audiovisual al préstamo.";
        }
        if (destinoUbicacion == null || destinoUbicacion.trim().isEmpty()) {
            return "Error: Debe indicar el destino o ubicación donde se usarán los equipos.";
        }
        if (fechaEjecucion == null || fechaDevolucionEsperada == null || !fechaEjecucion.isBefore(fechaDevolucionEsperada)) {
            return "Error: El rango de fechas y horas es inválido.";
        }
        if (servicioPrestamo.buscarPorCodigo(codigoFormatoFisico) != null) {
            return "Error: Ya existe un préstamo registrado con el código " + codigoFormatoFisico;
        }

        FuncionarioAudiovisuales funcionarioEntrega = null;
        if (idFuncionarioEntrega != null && !idFuncionarioEntrega.trim().isEmpty()) {
            funcionarioEntrega = servicioFuncionarioAudiovisuales.buscarPorDocumento(idFuncionarioEntrega);
            if (funcionarioEntrega == null) {
                return "Error: Funcionario de audiovisuales no encontrado.";
            }
        }

        List<Recurso> equiposSeleccionados = new ArrayList<>();
        boolean algunEquipoRequiereAval = false;

        for (String codigoEquipo : codigosEquipos) {
            Recurso recurso = servicioRecurso.buscarPorCodigo(codigoEquipo);
            if (recurso == null) {
                return "Error: El equipo con código '" + codigoEquipo + "' no existe.";
            }
            if (!servicioRecurso.consultarDisponibilidad(codigoEquipo)) {
                return "Error: El equipo '" + recurso.getDescripcion() + "' (" + codigoEquipo + ") no está disponible.";
            }
            if (recurso.isRequiereAutorizacionDocente()) {
                algunEquipoRequiereAval = true;
            }
            equiposSeleccionados.add(recurso);
        }

        Docente docenteAval = null;
        if (tipoSolicitante.equalsIgnoreCase("ESTUDIANTE") && algunEquipoRequiereAval) {
            if (codigoDocenteResponsable == null || codigoDocenteResponsable.trim().isEmpty()) {
                return "Error: Uno o más equipos seleccionados requieren el código de un docente responsable.";
            }
            docenteAval = servicioDocente.buscarPorCodigo(codigoDocenteResponsable);
            if (docenteAval == null) {
                return "Error: El docente responsable con código '" + codigoDocenteResponsable + "' no está registrado.";
            }
        } else if (codigoDocenteResponsable != null && !codigoDocenteResponsable.trim().isEmpty()) {
            docenteAval = servicioDocente.buscarPorCodigo(codigoDocenteResponsable);
        }

        Usuario solicitanteObj = null;
        boolean habilitado = false;

        switch (tipoSolicitante.toUpperCase()) {
            case "ESTUDIANTE":
                Estudiante est = servicioEstudiante.buscarPorCodigo(codigoSolicitante);
                if (est != null) {
                    List<Prestamo> historial = servicioPrestamo.obtenerPrestamosPorUsuario(est.getCodigoEstudiantil());
                    habilitado = servicioEstudiante.puedeSolicitarPrestamo(codigoSolicitante, fechaEjecucion, fechaDevolucionEsperada, historial);
                    solicitanteObj = est;
                }
                break;

            case "DOCENTE":
                Docente doc = servicioDocente.buscarPorCodigo(codigoSolicitante);
                if (doc != null) {
                    List<Prestamo> historial = servicioPrestamo.obtenerPrestamosPorUsuario(doc.getCodigoDocente());
                    habilitado = servicioDocente.puedeSolicitarPrestamo(codigoSolicitante, fechaEjecucion, fechaDevolucionEsperada, historial);
                    solicitanteObj = doc;
                }
                break;

            case "FUNCIONARIO":
                FuncionarioSolicitante func = servicioFuncionarioSolicitante.buscarPorCodigo(codigoSolicitante);
                if (func != null) {
                    List<Prestamo> historial = servicioPrestamo.obtenerPrestamosPorUsuario(func.getCodigoFuncionario());
                    habilitado = servicioFuncionarioSolicitante.puedeSolicitarPrestamo(codigoSolicitante, fechaEjecucion, fechaDevolucionEsperada, historial);
                    solicitanteObj = func;
                }
                break;

            default:
                return "Error: Tipo de solicitante no válido.";
        }

        if (solicitanteObj == null) {
            return "Error: No se encontró ningún " + tipoSolicitante.toLowerCase() + " con el código " + codigoSolicitante;
        }
        if (!habilitado) {
            return "Error: El solicitante tiene una sanción activa o presenta cruce de horarios con otro préstamo.";
        }

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setCodigoFormatoFisico(codigoFormatoFisico);
        nuevoPrestamo.setFechaEjecucion(fechaEjecucion);
        nuevoPrestamo.setFechaDevolucionEsperada(fechaDevolucionEsperada);
        nuevoPrestamo.setDestinoUbicacion(destinoUbicacion);
        nuevoPrestamo.setObservaciones(observaciones != null ? observaciones : "");
        nuevoPrestamo.setEquiposSolicitados(equiposSeleccionados);
        nuevoPrestamo.setUsuarioSolicitante(solicitanteObj);
        nuevoPrestamo.setDocenteResponsable(docenteAval);
        nuevoPrestamo.setFuncionarioEntrega(funcionarioEntrega);

        boolean registrado = servicioPrestamo.registrarPrestamo(nuevoPrestamo);
        if (registrado) {
            for (Recurso equipo : equiposSeleccionados) {
                servicioRecurso.cambiarEstadoRecurso(equipo.getCodigoEquipo(), EstadoRecurso.PRESTADO);
            }
            return "EXITO: Préstamo registrado correctamente con " + equiposSeleccionados.size() + " equipo(s).";
        }

        return "Error: Ocurrió un problema al guardar el préstamo.";
    }


    public String registrarDevolucionEquipo(String codigoFormatoFisico, String idFuncionarioRecibe,
                                            String observaciones, boolean algunEquipoDanado) {
        if (codigoFormatoFisico == null || codigoFormatoFisico.trim().isEmpty()) {
            return "Error: Ingrese el código del préstamo.";
        }

        Prestamo prestamo = servicioPrestamo.buscarPorCodigo(codigoFormatoFisico);
        if (prestamo == null) {
            return "Error: No se encontró ningún préstamo con ese código.";
        }
        if (prestamo.getEstadoPrestamo() == EstadoPrestamo.DEVUELTO || prestamo.getEstadoPrestamo() == EstadoPrestamo.CANCELADO) {
            return "Error: Este préstamo ya fue cerrado anteriormente.";
        }

        FuncionarioAudiovisuales funcionarioRecibe = null;
        if (idFuncionarioRecibe != null && !idFuncionarioRecibe.trim().isEmpty()) {
            funcionarioRecibe = servicioFuncionarioAudiovisuales.buscarPorDocumento(idFuncionarioRecibe);
            if (funcionarioRecibe == null) {
                return "Error: Funcionario de audiovisuales no encontrado.";
            }
        }


        boolean huboRetraso = servicioPrestamo.verificarRetrasoDevolucion(codigoFormatoFisico);


        prestamo.setFuncionarioRecibe(funcionarioRecibe);
        servicioPrestamo.registrarDevolucion(codigoFormatoFisico, LocalDateTime.now(), observaciones != null ? observaciones : "");


        EstadoRecurso estadoFinal = algunEquipoDanado ? EstadoRecurso.EN_MANTENIMIENTO : EstadoRecurso.DISPONIBLE;
        if (prestamo.getEquiposSolicitados() != null) {
            for (Recurso equipo : prestamo.getEquiposSolicitados()) {
                servicioRecurso.cambiarEstadoRecurso(equipo.getCodigoEquipo(), estadoFinal);
            }
        }


        if ((huboRetraso || algunEquipoDanado) && prestamo.getUsuarioSolicitante() instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) prestamo.getUsuarioSolicitante();
            servicioEstudiante.incrementarStrike(estudiante.getCodigoEstudiantil());

            if (estudiante.getSanciones() != null && !estudiante.getSanciones().isEmpty()) {
                Sancion ultimaSancion = estudiante.getSanciones().get(estudiante.getSanciones().size() - 1);
                if (servicioSancion.buscarPorId(ultimaSancion.getIdSancion()) == null) {
                    servicioSancion.agregarSancion(ultimaSancion);
                }
            }
            return "EXITO: Devolución registrada con novedad (retraso/daño). Se sumó 1 strike al estudiante.";
        }

        return "EXITO: Devolución registrada correctamente.";
    }

    public Prestamo buscarPrestamo(String codigoFormatoFisico) {
        if (codigoFormatoFisico == null || codigoFormatoFisico.trim().isEmpty()) {
            return null;
        }
        return servicioPrestamo.buscarPorCodigo(codigoFormatoFisico);
    }

    public List<Prestamo> listarTodos() {
        return servicioPrestamo.obtenerTodos();
    }
}