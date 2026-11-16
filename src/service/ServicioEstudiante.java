package service;

import model.Estudiante;
import model.Prestamo;
import model.Sancion;
import model.EstadoPrestamo;
import persistence.PersistenciaEstudiante;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServicioEstudiante {

    private ArrayList<Estudiante> estudiantes;
    private PersistenciaEstudiante persistencia;

    public ServicioEstudiante() {
        this.persistencia = new PersistenciaEstudiante();
        this.estudiantes = persistencia.leer();

        if (this.estudiantes == null) {
            this.estudiantes = new ArrayList<>();
        }
    }

    public void agregarEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
    }

    public Estudiante buscarPorCodigo(String codigoEstudiantil) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCodigoEstudiantil().equalsIgnoreCase(codigoEstudiantil)) {
                return estudiante;
            }
        }
        return null;
    }

    public ArrayList<Estudiante> obtenerTodos() {
        return this.estudiantes;
    }


    public void incrementarStrike(String codigoEstudiantil) {
        Estudiante estudiante = buscarPorCodigo(codigoEstudiantil);

        if (estudiante != null) {
            int strikesActuales = estudiante.getContadorStrikes() + 1;
            estudiante.setContadorStrikes(strikesActuales);

            if (strikesActuales >= 3) {
                estudiante.setEstaSancionado(true);

                LocalDateTime fechaInicio = LocalDateTime.now();
                LocalDateTime fechaFin = fechaInicio.plusDays(15);

                Sancion nuevaSancion = new Sancion(UUID.randomUUID().toString(), fechaInicio, fechaFin,
                        "Acumulación de 3 strikes", strikesActuales, true);

                if (estudiante.getSanciones() == null) {
                    estudiante.setSanciones(new ArrayList<>());
                }
                estudiante.getSanciones().add(nuevaSancion);
            }
        }
    }

    public void reiniciarStrikes(String codigoEstudiantil) {
        Estudiante estudiante = buscarPorCodigo(codigoEstudiantil);
        if (estudiante != null) {
            estudiante.setContadorStrikes(0);
            estudiante.setEstaSancionado(false);

            if (estudiante.getSanciones() != null) {
                for (Sancion sancion : estudiante.getSanciones()) {
                    sancion.setActiva(false);
                }
            }
        }
    }

    public boolean consultarEstadoSancion(String codigoEstudiantil) {
        Estudiante estudiante = buscarPorCodigo(codigoEstudiantil);

        if (estudiante != null && estudiante.isEstaSancionado()) {
            List<Sancion> sanciones = estudiante.getSanciones();

            if (sanciones != null) {
                for (Sancion sancion : sanciones) {
                    if (sancion.isActiva()) {
                        if (LocalDateTime.now().isAfter(sancion.getFechaFin())) {

                            sancion.setActiva(false);
                            estudiante.setEstaSancionado(false);
                            estudiante.setContadorStrikes(0);
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean puedeSolicitarPrestamo(String codigoEstudiantil, LocalDateTime fechaInicioNueva, LocalDateTime fechaFinNueva, List<Prestamo> prestamosDelEstudiante) {
        Estudiante estudiante = buscarPorCodigo(codigoEstudiantil);

        if (estudiante == null) {
            return false;
        }

        if (consultarEstadoSancion(codigoEstudiantil)) {
            return false; // Está sancionado activamente
        }

        if (prestamosDelEstudiante != null) {
            for (Prestamo prestamo : prestamosDelEstudiante) {

                boolean prestamoVigente = prestamo.getEstadoPrestamo() != EstadoPrestamo.DEVUELTO &&
                        prestamo.getEstadoPrestamo() != EstadoPrestamo.CANCELADO;

                if (prestamoVigente) {
                    LocalDateTime inicioExistente = prestamo.getFechaEjecucion();
                    LocalDateTime finExistente = prestamo.getFechaDevolucionEsperada();

                    boolean seCruzaHorario = fechaInicioNueva.isBefore(finExistente) && fechaFinNueva.isAfter(inicioExistente);

                    if (seCruzaHorario) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Map<String, String> getDatosAutocompletar(String codigoEstudiantil) {
        Estudiante estudiante = buscarPorCodigo(codigoEstudiantil);
        Map<String, String> datos = new HashMap<>();

        if (estudiante != null) {
            datos.put("nombreCompleto", estudiante.getNombreCompleto());
            datos.put("documentoIdentidad", estudiante.getDocumentoIdentidad());
            datos.put("telefono", estudiante.getTelefono());
            datos.put("codigoEstudiantil", estudiante.getCodigoEstudiantil());
            datos.put("correoInstitucional", estudiante.getCorreoInstitucional());
        }

        return datos;
    }


    public void guardarAlFinal() {
        persistencia.escribir(this.estudiantes);
    }
}