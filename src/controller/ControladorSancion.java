package controller;

import model.Estudiante;
import model.Sancion;
import service.ServicioEstudiante;
import service.ServicioSancion;

import java.util.List;

public class ControladorSancion {

    private ServicioSancion servicioSancion;
    private ServicioEstudiante servicioEstudiante;

    public ControladorSancion(ServicioSancion servicioSancion, ServicioEstudiante servicioEstudiante) {
        this.servicioSancion = servicioSancion;
        this.servicioEstudiante = servicioEstudiante;
    }


    public List<Sancion> listarTodasLasSanciones() {
        return servicioSancion.obtenerTodas();
    }


    public String quitarSancionManualmente(String idSancion, String codigoEstudiantil) {
        if (idSancion == null || idSancion.trim().isEmpty()) {
            return "Error: Debe seleccionar una sanción válida.";
        }
        if (codigoEstudiantil == null || codigoEstudiantil.trim().isEmpty()) {
            return "Error: Debe ingresar el código estudiantil.";
        }

        Sancion sancion = servicioSancion.buscarPorId(idSancion);
        if (sancion == null) {
            return "Error: No se encontró ninguna sanción con ese ID.";
        }

        Estudiante estudiante = servicioEstudiante.buscarPorCodigo(codigoEstudiantil);
        if (estudiante == null) {
            return "Error: No se encontró ningún estudiante con el código " + codigoEstudiantil;
        }

        servicioSancion.desactivarSancion(idSancion);
        servicioEstudiante.reiniciarStrikes(codigoEstudiantil);

        return "EXITO: Sanción levantada y contador de strikes reiniciado a 0.";
    }


    public String verificarEstadoSancionEstudiante(String codigoEstudiantil) {
        if (codigoEstudiantil == null || codigoEstudiantil.trim().isEmpty()) {
            return "Error: Ingrese el código estudiantil.";
        }

        Estudiante estudiante = servicioEstudiante.buscarPorCodigo(codigoEstudiantil);
        if (estudiante == null) {
            return "Error: Estudiante no encontrado.";
        }

        boolean sigueSancionado = servicioEstudiante.consultarEstadoSancion(codigoEstudiantil);

        if (sigueSancionado) {
            return "SANCIONADO: El estudiante tiene una sanción vigente (Strikes: " + estudiante.getContadorStrikes() + ").";
        } else {
            return "HABILITADO: El estudiante no tiene sanciones activas (Strikes: " + estudiante.getContadorStrikes() + ").";
        }
    }
}