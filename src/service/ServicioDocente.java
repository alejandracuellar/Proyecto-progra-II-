package service;

import model.Docente;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicioDocente {
    private List<Docente> docentes;

    public ServicioDocente() {
        this.docentes = new ArrayList<>();
    }

    public Docente buscarPorDocumento(String documento) throws Exception {
        for (Docente d : docentes) {
            if (d.getDocumentoIdentidad().equals(documento)) {
                return d;
            }
        }
        throw new Exception("El docente con documento " + documento + " no está registrado.");
    }

    public Map<String, String> autocompletarDocente(String documento) throws Exception {
        Docente docente = buscarPorDocumento(documento);
        return docente.getDatosAutocompletar();
    }

    public void registrarDocente(Docente docente) throws Exception {
        for (Docente d : docentes) {
            if (d.getDocumentoIdentidad().equals(docente.getDocumentoIdentidad())) {
                throw new Exception("Ya existe un docente registrado con el documento: " + docente.getDocumentoIdentidad());
            }
        }
        this.docentes.add(docente);
    }

    public List<Docente> getDocentes() { return docentes; }
}