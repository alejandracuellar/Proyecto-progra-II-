package service;

import model.Auditorio;
import model.HorarioDisponibilidad;
import persistence.PersistenciaAuditorio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioAuditorio {

    private ArrayList<Auditorio> auditorios;
    private PersistenciaAuditorio persistencia;

    public ServicioAuditorio() {
        this.persistencia = new PersistenciaAuditorio();
        this.auditorios = persistencia.leer();

        if (this.auditorios == null) {
            this.auditorios = new ArrayList<>();
        }
    }

    public void agregarAuditorio(Auditorio auditorio) {
        this.auditorios.add(auditorio);
    }

    public Auditorio buscarPorNombre(String nombre) {
        for (Auditorio auditorio : auditorios) {
            if (auditorio.getNombreAuditorio().equalsIgnoreCase(nombre)) {
                return auditorio;
            }
        }
        return null;
    }

    public ArrayList<Auditorio> obtenerTodos() {
        return this.auditorios;
    }

    public List<HorarioDisponibilidad> consultarHorariosDisponibles(String nombreAuditorio, LocalDateTime fecha) {
        Auditorio auditorio = buscarPorNombre(nombreAuditorio);
        List<HorarioDisponibilidad> horariosPermitidos = new ArrayList<>();

        if (auditorio != null && auditorio.getHorariosDisponibles() != null) {
            String diaSemana = obtenerDiaSemana(fecha);

            for (HorarioDisponibilidad horario : auditorio.getHorariosDisponibles()) {

                if (horario.getDiaSemana().equalsIgnoreCase(diaSemana) && !horario.isEstaOcupado()) {
                    horariosPermitidos.add(horario);
                }
            }
        }
        return horariosPermitidos;
    }

    public boolean verificarDisponibilidad(String nombreAuditorio, LocalDateTime fecha, String horaInicio, String horaFin) {
        Auditorio auditorio = buscarPorNombre(nombreAuditorio);

        if (auditorio != null && auditorio.getHorariosDisponibles() != null) {
            String diaSemana = obtenerDiaSemana(fecha);

            for (HorarioDisponibilidad horario : auditorio.getHorariosDisponibles()) {
                if (horario.getDiaSemana().equalsIgnoreCase(diaSemana) && horario.getHoraInicio().equals(horaInicio) &&
                        horario.getHoraFin().equals(horaFin) && !horario.isEstaOcupado()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean reservarHorario(String nombreAuditorio, String diaSemana, String horaInicio, String horaFin) {
        Auditorio auditorio = buscarPorNombre(nombreAuditorio);

        if (auditorio != null && auditorio.getHorariosDisponibles() != null) {
            for (HorarioDisponibilidad horario : auditorio.getHorariosDisponibles()) {
                if (horario.getDiaSemana().equalsIgnoreCase(diaSemana)
                        && horario.getHoraInicio().equals(horaInicio)
                        && horario.getHoraFin().equals(horaFin)
                        && !horario.isEstaOcupado()) {
                    horario.setEstaOcupado(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void guardarAlFinal() {
        persistencia.escribir(this.auditorios);
    }

    private String obtenerDiaSemana(LocalDateTime fecha) {
        switch (fecha.getDayOfWeek()) {
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miercoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sabado";
            case SUNDAY: return "Domingo";
            default: return "";
        }
    }
}