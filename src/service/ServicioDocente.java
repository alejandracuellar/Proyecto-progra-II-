package service;

import model.Docente;
import java.util.ArrayList;

public class ServicioDocente {

    private ArrayList<Docente> listaDocentes;

    public ServicioDocente(ArrayList<Docente> listaDocentes) {
        this.listaDocentes = (listaDocentes != null) ? listaDocentes : new ArrayList<>();
    }

}