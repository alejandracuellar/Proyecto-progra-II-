package service;

import model.Recurso;
import java.util.ArrayList;

public class ServicioRecurso {

    private ArrayList<Recurso> listaRecursos;

    public ServicioRecurso(ArrayList<Recurso> listaRecursos) {
        this.listaRecursos = (listaRecursos != null) ? listaRecursos : new ArrayList<>();
    }

}