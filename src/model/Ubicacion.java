package model;

public class Ubicacion {
    private String bloqueEdificio;
    private String numeroSalon;

    public Ubicacion(String bloqueEdificio, String numeroSalon) {
        this.bloqueEdificio = bloqueEdificio;
        this.numeroSalon = numeroSalon;
    }

    public String getBloqueEdificio() {
        return bloqueEdificio;
    }

    public void setBloqueEdificio(String bloqueEdificio) {
        this.bloqueEdificio = bloqueEdificio;
    }

    public String getNumeroSalon() {
        return numeroSalon;
    }

    public void setNumeroSalon(String numeroSalon) {
        this.numeroSalon = numeroSalon;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "bloque='" + bloqueEdificio + '\'' +
                ", numeroSalon='" + numeroSalon + '\'' +
                '}';
    }
}
