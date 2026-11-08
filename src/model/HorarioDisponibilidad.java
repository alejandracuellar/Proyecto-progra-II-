package model;

public class HorarioDisponibilidad {
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private boolean estaOcupado;

    public HorarioDisponibilidad() {}

    public HorarioDisponibilidad(String diaSemana, String horaInicio, String horaFin, boolean estaOcupado) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estaOcupado = estaOcupado;
    }

    public String getDiaSemana() {
        return diaSemana;
    }
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public boolean isEstaOcupado() {
        return estaOcupado;
    }
    public void setEstaOcupado(boolean estaOcupado) {
        this.estaOcupado = estaOcupado;
    }

    @Override
    public String toString() {
        return "HorarioDisponibilidad{" +
                "diaSemana='" + diaSemana + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", estaOcupado=" + estaOcupado +
                '}';
    }
}