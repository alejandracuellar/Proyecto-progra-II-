package service;

import model.Recurso;
import persistence.PersistenciaInventario;

import java.util.List;
import java.util.Map;

public class ServicioRecurso {
    private List<Recurso> inventario;
    private final PersistenciaInventario persistencia;

    public ServicioRecurso(PersistenciaInventario persistencia) {
        this.persistencia = persistencia;
        this.inventario = persistencia.leerInventario();
    }

    public Recurso buscarPorCodigo(String codigo) throws Exception {
        for (Recurso r : inventario) {
            if (r.getCodigoEquipo().equalsIgnoreCase(codigo)) {
                return r;
            }
        }
        throw new Exception("El recurso con código '" + codigo + "' no existe en el inventario.");
    }

    public Map<String, String> autocompletarRecurso(String codigo) throws Exception {
        Recurso recurso = buscarPorCodigo(codigo);
        return recurso.getDatosAutocompletar();
    }

    public void agregarEquipoInventario(Recurso recurso) throws Exception {
        for (Recurso r : inventario) {
            if (r.getCodigoEquipo().equalsIgnoreCase(recurso.getCodigoEquipo())) {
                throw new Exception("Ya existe un equipo registrado con el código: " + recurso.getCodigoEquipo());
            }
        }
        this.inventario.add(recurso);
        persistencia.escribirInventario(this.inventario);
    }

    public void guardarInventario() {
        persistencia.escribirInventario(this.inventario);
    }

    public List<Recurso> getInventario() { return inventario; }
}