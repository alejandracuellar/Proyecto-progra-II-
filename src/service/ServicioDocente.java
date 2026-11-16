package service;

import model.Docente;
import model.EstadoPrestamo;
import model.Prestamo;
import persistence.PersistenciaDocente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioDocente {

    private ArrayList<Docente> docentes;
    private PersistenciaDocente persistencia;

    public ServicioDocente() {
        this.persistencia = new PersistenciaDocente();
        this.docentes = persistencia.leer();

        if (this.docentes == null) {
            this.docentes = new ArrayList<>();
        }
    }

    public void agregarDocente(Docente docente) {
        this.docentes.add(docente);
    }

    public Docente buscarPorCodigo(String codigoDocente) {
        for (Docente docente : docentes) {
            if (docente.getCodigoDocente().equalsIgnoreCase(codigoDocente)) {
                return docente;
            }
        }
        return null;
    }

    public Docente buscarPorDocumento(String documentoIdentidad) {
        for (Docente docente : docentes) {
            if (docente.getDocumentoIdentidad().equalsIgnoreCase(documentoIdentidad)) {
                return docente;
            }
        }
        return null;
    }

    public ArrayList<Docente> obtenerTodos() {
        return this.docentes;
    }

    public boolean puedeSolicitarPrestamo(String codigoDocente, LocalDateTime fechaInicioNueva, LocalDateTime fechaFinNueva, List<Prestamo> prestamosDelDocente) {
        Docente docente = buscarPorCodigo(codigoDocente);

        if (docente == null) {
            return false;
        }

        if (prestamosDelDocente != null) {
            for (Prestamo prestamo : prestamosDelDocente) {

                boolean prestamoVigente = prestamo.getEstadoPrestamo() != EstadoPrestamo.DEVUELTO &&
                        prestamo.getEstadoPrestamo() != EstadoPrestamo.CANCELADO;

                if (prestamoVigente) {
                    LocalDateTime inicioExistente = prestamo.getFechaEjecucion();
                    LocalDateTime finExistente = prestamo.getFechaDevolucionEsperada();

                    boolean otroPrestamoEsteHorario = fechaInicioNueva.isBefore(finExistente) && fechaFinNueva.isAfter(inicioExistente);

                    if (otroPrestamoEsteHorario) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Map<String, String> getDatosAutocompletar(String codigoDocente) {
        Docente docente = buscarPorCodigo(codigoDocente);
        Map<String, String> datos = new HashMap<>();

        if (docente != null) {
            datos.put("nombreCompleto", docente.getNombreCompleto());
            datos.put("documentoIdentidad", docente.getDocumentoIdentidad());
            datos.put("telefono", docente.getTelefono());
            datos.put("codigoDocente", docente.getCodigoDocente());
            datos.put("correoInstitucional", docente.getCorreoInstitucional());
            datos.put("escuelaDependencia", docente.getEscuelaDependencia());
        }

        return datos;
    }

    public void guardarAlFinal() {
        persistencia.escribir(this.docentes);
    }
}