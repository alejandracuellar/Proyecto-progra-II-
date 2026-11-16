package controller;

import model.Auditorio;
import model.HorarioDisponibilidad;
import service.ServicioAuditorio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorAuditorio {

    private ServicioAuditorio servicioAuditorio;

    public ControladorAuditorio(ServicioAuditorio servicioAuditorio) {
        this.servicioAuditorio = servicioAuditorio;
    }

    public String registrarAuditorio(String nombreAuditorio, int capacidad, String ubicacion, List<HorarioDisponibilidad> horariosDisponibles) {

        if (nombreAuditorio == null || nombreAuditorio.trim().isEmpty()) {
            return "Error: El nombre del auditorio es obligatorio.";
        }
        if (capacidad <= 0) {
            return "Error: La capacidad debe ser mayor a cero.";
        }
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            return "Error: Debe especificar la ubicación del auditorio.";
        }


        if (servicioAuditorio.buscarPorNombre(nombreAuditorio) != null) {
            return "Error: Ya existe un auditorio registrado con el nombre '" + nombreAuditorio + "'.";
        }


        Auditorio nuevoAuditorio = new Auditorio();
        nuevoAuditorio.setNombreAuditorio(nombreAuditorio);
        nuevoAuditorio.setCapacidad(capacidad);
        nuevoAuditorio.setUbicacion(ubicacion);
        nuevoAuditorio.setHorariosDisponibles(horariosDisponibles != null ? horariosDisponibles : new ArrayList<>());

        servicioAuditorio.agregarAuditorio(nuevoAuditorio);

        return "EXITO: Auditorio registrado correctamente.";
    }


    public Auditorio buscarAuditorio(String nombreAuditorio) {
        if (nombreAuditorio == null || nombreAuditorio.trim().isEmpty()) {
            return null;
        }
        return servicioAuditorio.buscarPorNombre(nombreAuditorio);
    }

    public ArrayList<Auditorio> listarTodos() {
        return servicioAuditorio.obtenerTodos();
    }

    public List<HorarioDisponibilidad> obtenerHorariosLibres(String nombreAuditorio, LocalDateTime fecha) {
        if (nombreAuditorio == null || nombreAuditorio.trim().isEmpty() || fecha == null) {
            return new ArrayList<>();
        }
        return servicioAuditorio.consultarHorariosDisponibles(nombreAuditorio, fecha);
    }

    public String verificarFranjaHoraria(String nombreAuditorio, LocalDateTime fecha, String horaInicio, String horaFin) {

        if (nombreAuditorio == null || nombreAuditorio.trim().isEmpty()) {
            return "Error: Debe seleccionar un auditorio.";
        }
        if (fecha == null) {
            return "Error: Debe seleccionar una fecha válida.";
        }
        if (horaInicio == null || horaInicio.trim().isEmpty() || horaFin == null || horaFin.trim().isEmpty()) {
            return "Error: Debe especificar la hora de inicio y la hora de fin.";
        }


        if (servicioAuditorio.buscarPorNombre(nombreAuditorio) == null) {
            return "Error: El auditorio '" + nombreAuditorio + "' no existe.";
        }

        boolean disponible = servicioAuditorio.verificarDisponibilidad(nombreAuditorio, fecha, horaInicio, horaFin);

        if (disponible) {
            return "DISPONIBLE: El auditorio está libre en la franja de " + horaInicio + " a " + horaFin + ".";
        } else {
            return "NO DISPONIBLE: Esa franja horaria ya está ocupada o no está habilitada para ese día.";
        }
    }
}