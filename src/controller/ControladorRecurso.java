package controller;

import model.EstadoRecurso;
import model.Recurso;
import model.Ubicacion;
import service.ServicioRecurso;
import service.ServicioUbicacion;

import java.util.List;

public class ControladorRecurso {

    private ServicioRecurso servicioRecurso;
    private ServicioUbicacion servicioUbicacion;

    public ControladorRecurso(ServicioRecurso servicioRecurso) {
        this.servicioRecurso = servicioRecurso;
        this.servicioUbicacion = servicioUbicacion;
    }

    public String registrarRecurso(String codigoEquipo, String descripcion, boolean requiereAutorizacionDocente,
                                   Ubicacion ubicacionActual, String marca, String numeroSerie,
                                   String categoria, String especificacionesAdicionales) {


        if (codigoEquipo == null || codigoEquipo.trim().isEmpty()) {
            return "Error: El código del equipo es obligatorio.";
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return "Error: La descripción del recurso es obligatoria.";
        }
        if (ubicacionActual == null) {
            return "Error: Debe asignar una ubicación inicial al equipo.";
        }
        if (marca == null || marca.trim().isEmpty()) {
            return "Error: La marca del equipo es obligatoria.";
        }
        if (numeroSerie == null || numeroSerie.trim().isEmpty()) {
            return "Error: El número de serie es obligatorio.";
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            return "Error: La categoría es obligatoria.";
        }

        if (servicioRecurso.buscarPorCodigo(codigoEquipo) != null) {
            return "Error: Ya existe un equipo registrado con el código " + codigoEquipo;
        }


        Recurso nuevoRecurso = new Recurso(codigoEquipo, descripcion, EstadoRecurso.DISPONIBLE,
                requiereAutorizacionDocente, ubicacionActual, marca, numeroSerie, categoria,
                especificacionesAdicionales != null ? especificacionesAdicionales : ""
        );

        servicioRecurso.agregarRecurso(nuevoRecurso);

        return "EXITO: Recurso audiovisual registrado en el inventario.";
    }

    public String actualizarEstadoRecurso(String codigoEquipo, EstadoRecurso nuevoEstado) {
        if (codigoEquipo == null || codigoEquipo.trim().isEmpty()) {
            return "Error: Ingrese el código del equipo.";
        }
        if (nuevoEstado == null) {
            return "Error: Debe seleccionar un estado válido.";
        }

        Recurso recurso = servicioRecurso.buscarPorCodigo(codigoEquipo);
        if (recurso == null) {
            return "Error: No se encontró ningún equipo con ese código.";
        }

        servicioRecurso.cambiarEstadoRecurso(codigoEquipo, nuevoEstado);
        return "EXITO: Estado del equipo actualizado a " + nuevoEstado.name();
    }

    public Recurso buscarRecurso(String codigoEquipo) {
        if (codigoEquipo == null || codigoEquipo.trim().isEmpty()) {
            return null;
        }
        return servicioRecurso.buscarPorCodigo(codigoEquipo);
    }

    public List<Recurso> listarTodos() {
        return servicioRecurso.obtenerTodos();
    }

    public List<Ubicacion> listarUbicaciones() {
        return servicioUbicacion.obtenerTodasLasUbicaciones();
    }

    public String registrarUbicacion(String bloque, String salon) {
        if (bloque == null || bloque.trim().isEmpty() || salon == null || salon.trim().isEmpty()) {
            return "Error: El bloque y el salón son obligatorios.";
        }
        boolean agregada = servicioUbicacion.agregarUbicacion(bloque, salon);
        return agregada ? "EXITO: Ubicación registrada." : "Error: Esa ubicación ya existe.";
    }
}