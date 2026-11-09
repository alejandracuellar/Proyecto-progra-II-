package service;

import model.Sancion;
import java.util.ArrayList;

public class ServicioSancion {

    private ArrayList<Sancion> listaSanciones;

    public ServicioSancion(ArrayList<Sancion> listaSanciones) {
        this.listaSanciones = (listaSanciones != null) ? listaSanciones : new ArrayList<>();
    }

}