package service;

import model.Ubicacion;
import java.util.ArrayList;
import java.util.List;

public class ServicioUbicacion {
    private List<Ubicacion> ubicaciones;

    public ServicioUbicacion() {
        this.ubicaciones = new ArrayList<>();
    }

    public Ubicacion buscarPorBloqueYSalon(String bloque, String salon) throws Exception {
        for (Ubicacion u : ubicaciones) {
            if (u.getBloqueEdificio().equalsIgnoreCase(bloque) && u.getNumeroSalon().equalsIgnoreCase(salon)) {
                return u;
            }
        }
        throw new Exception("La ubicación Bloque " + bloque + " - Salón " + salon + " no existe.");
    }

    public void registrarUbicacion(Ubicacion ubicacion) {
        this.ubicaciones.add(ubicacion);
    }

    public List<Ubicacion> getUbicaciones() { return ubicaciones; }
}