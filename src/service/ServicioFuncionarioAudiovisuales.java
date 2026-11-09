package service;

import model.FuncionarioAudiovisuales;
import java.util.ArrayList;
import java.util.List;

public class ServicioFuncionarioAudiovisuales {
    private List<FuncionarioAudiovisuales> funcionarios;

    public ServicioFuncionarioAudiovisuales() {
        this.funcionarios = new ArrayList<>();
    }

    public FuncionarioAudiovisuales buscarPorId(String id) throws Exception {
        for (FuncionarioAudiovisuales f : funcionarios) {
            if (f.getId().equalsIgnoreCase(id)) {
                return f;
            }
        }
        throw new Exception("El funcionario de audiovisuales con ID " + id + " no está registrado.");
    }

    public void registrarFuncionario(FuncionarioAudiovisuales funcionario) throws Exception {
        for (FuncionarioAudiovisuales f : funcionarios) {
            if (f.getId().equalsIgnoreCase(funcionario.getId())) {
                throw new Exception("Ya existe un funcionario registrado con el ID: " + funcionario.getId());
            }
        }
        this.funcionarios.add(funcionario);
    }

    public List<FuncionarioAudiovisuales> getFuncionarios() { return funcionarios; }
}