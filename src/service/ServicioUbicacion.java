package service;

import model.Ubicacion;
import persistence.PersistenciaUbicacion;
import java.util.ArrayList;
import java.util.List;

public class ServicioUbicacion {

    private PersistenciaUbicacion persistencia;
    private List<Ubicacion> ubicaciones;

    public ServicioUbicacion() {
        this.persistencia = new PersistenciaUbicacion();
        this.ubicaciones = persistencia.leer();
    }

    public List<Ubicacion> obtenerTodasLasUbicaciones() {
        return this.ubicaciones;
    }

    public boolean agregarUbicacion(String bloque, String salon) {
        for (Ubicacion u : ubicaciones) {
            if (u.getBloqueEdificio().equalsIgnoreCase(bloque) &&
                    u.getNumeroSalon().equalsIgnoreCase(salon)) {
                return false;
            }
        }

        Ubicacion nuevaUbicacion = new Ubicacion(bloque, salon);
        ubicaciones.add(nuevaUbicacion);
        persistencia.escribir((ArrayList<Ubicacion>) ubicaciones);
        return true;
    }
}