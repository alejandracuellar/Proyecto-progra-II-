package service;

import model.Auditorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicioAuditorio {
    private List<Auditorio> auditorios;

    public ServicioAuditorio() {
        this.auditorios = new ArrayList<>();
    }

    public Auditorio buscarPorNombre(String nombre) throws Exception {
        for (Auditorio a : auditorios) {
            if (a.getNombreAuditorio().equalsIgnoreCase(nombre)) {
                return a;
            }
        }
        throw new Exception("El auditorio '" + nombre + "' no existe en el sistema.");
    }

    public Map<String, String> autocompletarAuditorio(String nombre) throws Exception {
        Auditorio auditorio = buscarPorNombre(nombre);
        return auditorio.getDatosAutocompletar();
    }

    public void registrarAuditorio(Auditorio auditorio) throws Exception {
        for (Auditorio a : auditorios) {
            if (a.getNombreAuditorio().equalsIgnoreCase(auditorio.getNombreAuditorio())) {
                throw new Exception("Ya existe un auditorio registrado con el nombre: " + auditorio.getNombreAuditorio());
            }
        }
        this.auditorios.add(auditorio);
    }

    public List<Auditorio> getAuditorios() { return auditorios; }
}