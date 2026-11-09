package service;

import model.FuncionarioAudiovisuales;
import java.util.ArrayList;


public class ServicioFuncionarioAudiovisuales {

    private ArrayList<FuncionarioAudiovisuales> listaFuncionariosAudiovisuales;

    public ServicioFuncionarioAudiovisuales(ArrayList<FuncionarioAudiovisuales> listaFuncionariosAudiovisuales) {
        this.listaFuncionariosAudiovisuales = (listaFuncionariosAudiovisuales != null) ? listaFuncionariosAudiovisuales : new ArrayList<>();
    }

}