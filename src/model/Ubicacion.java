package model;

public class Ubicacion {
    private String bloque;
    private String numeroSalon;

    public Ubicacion(String bloque, String numeroSalon) {
        this.bloque = bloque;
        this.numeroSalon = numeroSalon;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
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
                "bloque='" + bloque + '\'' +
                ", numeroSalon='" + numeroSalon + '\'' +
                '}';
    }
}
