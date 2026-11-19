package service;

import model.Auditorio;
import model.HorarioDisponibilidad;
import persistence.PersistenciaAuditorio;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ServicioAuditorio {

    private ArrayList<Auditorio> auditorios;
    private PersistenciaAuditorio persistencia;
    private final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("H:mm");

    public ServicioAuditorio() {
        this.persistencia = new PersistenciaAuditorio();
        this.auditorios = persistencia.leer();

        if (this.auditorios == null) {
            this.auditorios = new ArrayList<>();
        }

        for (Auditorio aud : this.auditorios) {
            if (aud.getHorariosDisponibles() == null) {
                aud.setHorariosDisponibles(new ArrayList<>());
            } else {
                aud.getHorariosDisponibles().removeIf(h -> !h.isEstaOcupado());
            }
        }
    }

    public void agregarAuditorio(Auditorio auditorio) {
        if (auditorio.getHorariosDisponibles() == null) {
            auditorio.setHorariosDisponibles(new ArrayList<>());
        }
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
        List<HorarioDisponibilidad> disponibles = new ArrayList<>();

        if (auditorio != null) {
            String diaSemana = obtenerDiaSemana(fecha);
            for (int h = 7; h < 21; h++) {
                String ini = String.format("%02d:00", h);
                String fin = String.format("%02d:00", h + 1);
                if (!hayCruceHorario(auditorio, diaSemana, LocalTime.of(h, 0), LocalTime.of(h + 1, 0))) {
                    HorarioDisponibilidad libre = new HorarioDisponibilidad();
                    libre.setDiaSemana(diaSemana);
                    libre.setHoraInicio(ini);
                    libre.setHoraFin(fin);
                    libre.setEstaOcupado(false);
                    disponibles.add(libre);
                }
            }
        }
        return disponibles;
    }

    public boolean verificarDisponibilidad(String nombreAuditorio, LocalDateTime fecha, String horaInicio, String horaFin) {
        Auditorio auditorio = buscarPorNombre(nombreAuditorio);
        if (auditorio == null) return false;

        try {
            LocalTime ini = LocalTime.parse(horaInicio.trim(), FORMATO_HORA);
            LocalTime fin = LocalTime.parse(horaFin.trim(), FORMATO_HORA);
            if (!ini.isBefore(fin) || ini.isBefore(LocalTime.of(7, 0)) || fin.isAfter(LocalTime.of(21, 0))) {
                return false;
            }
            return !hayCruceHorario(auditorio, obtenerDiaSemana(fecha), ini, fin);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean reservarHorario(String nombreAuditorio, String diaSemana, String horaInicio, String horaFin) {
        Auditorio auditorio = buscarPorNombre(nombreAuditorio);
        if (auditorio == null) return false;

        try {
            LocalTime ini = LocalTime.parse(horaInicio.trim(), FORMATO_HORA);
            LocalTime fin = LocalTime.parse(horaFin.trim(), FORMATO_HORA);

            if (!ini.isBefore(fin) || ini.isBefore(LocalTime.of(7, 0)) || fin.isAfter(LocalTime.of(21, 0))) {
                return false;
            }

            if (auditorio.getHorariosDisponibles() == null) {
                auditorio.setHorariosDisponibles(new ArrayList<>());
            }

            if (hayCruceHorario(auditorio, diaSemana, ini, fin)) {
                return false;
            }

            HorarioDisponibilidad nuevaReserva = new HorarioDisponibilidad();
            nuevaReserva.setDiaSemana(diaSemana);
            nuevaReserva.setHoraInicio(String.format("%02d:%02d", ini.getHour(), ini.getMinute()));
            nuevaReserva.setHoraFin(String.format("%02d:%02d", fin.getHour(), fin.getMinute()));
            nuevaReserva.setEstaOcupado(true);

            auditorio.getHorariosDisponibles().add(nuevaReserva);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean hayCruceHorario(Auditorio auditorio, String diaSemana, LocalTime nuevaIni, LocalTime nuevaFin) {
        if (auditorio.getHorariosDisponibles() == null) return false;

        for (HorarioDisponibilidad h : auditorio.getHorariosDisponibles()) {
            if (h.isEstaOcupado() && h.getDiaSemana() != null && h.getDiaSemana().equalsIgnoreCase(diaSemana)) {
                try {
                    LocalTime exIni = LocalTime.parse(h.getHoraInicio().trim(), FORMATO_HORA);
                    LocalTime exFin = LocalTime.parse(h.getHoraFin().trim(), FORMATO_HORA);
                    // Hay cruce si el nuevo inicio es antes del fin existente Y el nuevo fin es después del inicio existente
                    if (nuevaIni.isBefore(exFin) && nuevaFin.isAfter(exIni)) {
                        return true;
                    }
                } catch (DateTimeParseException ignored) {
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