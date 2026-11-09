package service;

import model.Auditorio;
import java.util.ArrayList;

public class ServicioAuditorio {

    private ArrayList<Auditorio> listaAuditorios;

    public ServicioAuditorio(ArrayList<Auditorio> listaAuditorios) {
        this.listaAuditorios = (listaAuditorios != null) ? listaAuditorios : new ArrayList<>();
    }

}