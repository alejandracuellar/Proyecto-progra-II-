package service;

import model.Estudiante;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicioEstudiante {
    private List<Estudiante> estudiantes;

    public ServicioEstudiante() {
        this.estudiantes = new ArrayList<>();
    }

    public Estudiante buscarPorDocumento(String documento) throws Exception {
        for (Estudiante e : estudiantes) {
            if (e.getDocumentoIdentidad().equals(documento)) {
                return e;
            }
        }
        throw new Exception("El estudiante con documento " + documento + " no está registrado.");
    }

    public Map<String, String> autocompletarEstudiante(String documento) throws Exception {
        Estudiante estudiante = buscarPorDocumento(documento);
        return estudiante.getDatosAutocompletar();
    }

    public void registrarEstudiante(Estudiante estudiante) throws Exception {
        for (Estudiante e : estudiantes) {
            if (e.getDocumentoIdentidad().equals(estudiante.getDocumentoIdentidad())) {
                throw new Exception("Ya existe un estudiante registrado con el documento: " + estudiante.getDocumentoIdentidad());
            }
        }
        this.estudiantes.add(estudiante);
    }

    public List<Estudiante> getEstudiantes() { return estudiantes; }
}