package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auditorio {
    private String nombreAuditorio;
    private int capacidad;
    private String ubicacion;
    private List<HorarioDisponibilidad> horariosDisponibles;

    public Auditorio(String nombreAuditorio, int capacidad, String ubicacion, List<HorarioDisponibilidad> horariosDisponibles) {
        this.nombreAuditorio = nombreAuditorio;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.horariosDisponibles = new ArrayList<>();
    }

    public String getNombreAuditorio() {
        return nombreAuditorio;
    }

    public void setNombreAuditorio(String nombreAuditorio) {
        this.nombreAuditorio = nombreAuditorio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<HorarioDisponibilidad> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public void setHorariosDisponibles(List<HorarioDisponibilidad> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }

    public Map<String, String> getDatosAutocompletar() {
        Map<String, String> datos = new HashMap<>();
        datos.put("nombreAuditorio", this.nombreAuditorio);
        datos.put("capacidad", String.valueOf(this.capacidad));
        datos.put("ubicacion", this.ubicacion);
        return datos;
    }
}
