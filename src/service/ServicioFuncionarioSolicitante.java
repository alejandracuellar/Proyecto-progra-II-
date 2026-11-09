package service;

import model.FuncionarioSolicitante;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicioFuncionarioSolicitante {
    private List<FuncionarioSolicitante> funcionariosSolicitantes;

    public ServicioFuncionarioSolicitante() {
        this.funcionariosSolicitantes = new ArrayList<>();
    }

    public FuncionarioSolicitante buscarPorDocumento(String documento) throws Exception {
        for (FuncionarioSolicitante f : funcionariosSolicitantes) {
            if (f.getDocumentoIdentidad().equals(documento)) {
                return f;
            }
        }
        throw new Exception("El funcionario solicitante con documento " + documento + " no está registrado.");
    }

    public Map<String, String> autocompletarFuncionario(String documento) throws Exception {
        FuncionarioSolicitante funcionario = buscarPorDocumento(documento);
        return funcionario.getDatosAutocompletar();
    }

    public void registrarFuncionarioSolicitante(FuncionarioSolicitante funcionario) throws Exception {
        for (FuncionarioSolicitante f : funcionariosSolicitantes) {
            if (f.getDocumentoIdentidad().equals(funcionario.getDocumentoIdentidad())) {
                throw new Exception("Ya existe un funcionario solicitante registrado con el documento: " + funcionario.getDocumentoIdentidad());
            }
        }
        this.funcionariosSolicitantes.add(funcionario);
    }

    public List<FuncionarioSolicitante> getFuncionariosSolicitantes() { return funcionariosSolicitantes; }
}