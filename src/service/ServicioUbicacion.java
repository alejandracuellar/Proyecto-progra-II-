package service;

import model.Ubicacion;
import java.util.ArrayList;

public class ServicioUbicacion {

    private ArrayList<Ubicacion> listaUbicaciones;

    public ServicioUbicacion(ArrayList<Ubicacion> listaUbicaciones) {
        this.listaUbicaciones = (listaUbicaciones != null) ? listaUbicaciones : new ArrayList<>();
    }

}