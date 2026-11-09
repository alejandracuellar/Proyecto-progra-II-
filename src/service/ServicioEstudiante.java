package service;

import model.Estudiante;
import java.util.ArrayList;
import java.util.List;

public class ServicioEstudiante {

    private ArrayList<Estudiante> listaEstudiantes;

    public ServicioEstudiante(ArrayList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = (listaEstudiantes != null) ? listaEstudiantes : new ArrayList<>();
    }

}