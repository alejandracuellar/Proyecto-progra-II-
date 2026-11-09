package service;

import model.FuncionarioSolicitante;
import java.util.ArrayList;


public class ServicioFuncionarioSolicitante {

    private ArrayList<FuncionarioSolicitante> listaFuncionariosSolicitantes;

    public ServicioFuncionarioSolicitante(ArrayList<FuncionarioSolicitante> listaFuncionariosSolicitantes) {
        this.listaFuncionariosSolicitantes = (listaFuncionariosSolicitantes != null) ? listaFuncionariosSolicitantes : new ArrayList<>();
    }

}