package service;

import model.Prestamo;
import java.util.ArrayList;


public class ServicioPrestamo {

    private ArrayList<Prestamo> listaPrestamos;

    public ServicioPrestamo(ArrayList<Prestamo> listaPrestamos) {
        this.listaPrestamos = (listaPrestamos != null) ? listaPrestamos : new ArrayList<>();
    }

}