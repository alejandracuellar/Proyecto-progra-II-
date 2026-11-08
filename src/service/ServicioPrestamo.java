package service;

import model.Prestamo;
import model.Recurso;
import persistence.PersistenciaJson;

import java.util.ArrayList;
import java.util.List;

public class ServicioPrestamo {

    private PersistenciaJson persistenciaJson = new PersistenciaJson();
    private ArrayList<Prestamo> prestamos;
    private List<Recurso> recursos = new ArrayList<>();

    public ServicioPrestamo(){
        this.prestamos = persistenciaJson.leer();

    }
}
