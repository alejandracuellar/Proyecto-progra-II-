package service;

import model.*;
import persistence.PersistenciaPrestamo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioPrestamo {

    private ArrayList<Prestamo> prestamos;
    private PersistenciaPrestamo persistencia;

    public ServicioPrestamo() {
        this.persistencia = new PersistenciaPrestamo();
        this.prestamos = persistencia.leer();

        if (this.prestamos == null) {
            this.prestamos = new ArrayList<>();
        }
    }

    public Prestamo buscarPorCodigo(String codigoFormatoFisico) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigoFormatoFisico().equalsIgnoreCase(codigoFormatoFisico)) {
                return prestamo;
            }
        }
        return null;
    }

    public ArrayList<Prestamo> obtenerTodos() {
        return this.prestamos;
    }



    public boolean registrarPrestamo(Prestamo prestamo) {
        if (prestamo != null) {
            prestamo.setFechaSolicitud(LocalDateTime.now());
            prestamo.setEstadoPrestamo(EstadoPrestamo.ACTIVO);
            this.prestamos.add(prestamo);
            return true;
        }
        return false;
    }

    public void registrarDevolucion(String codigoFormatoFisico, LocalDateTime fechaReal, String observaciones) {
        Prestamo prestamo = buscarPorCodigo(codigoFormatoFisico);
        if (prestamo != null) {
            prestamo.setFechaDevolucionReal(fechaReal);
            prestamo.setObservaciones(observaciones);
            prestamo.setEstadoPrestamo(EstadoPrestamo.DEVUELTO);
        }
    }

    public boolean verificarRetrasoDevolucion(String codigoFormatoFisico) {
        Prestamo prestamo = buscarPorCodigo(codigoFormatoFisico);

        if (prestamo != null) {

            if (prestamo.getEstadoPrestamo() == EstadoPrestamo.DEVUELTO || prestamo.getEstadoPrestamo() == EstadoPrestamo.CANCELADO) {
                return false;
            }

            return LocalDateTime.now().isAfter(prestamo.getFechaDevolucionEsperada());
        }
        return false;
    }

    public List<Prestamo> obtenerPrestamosPorUsuario(String codigoUsuario) {
        List<Prestamo> prestamosDelUsuario = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            Usuario u = prestamo.getUsuarioSolicitante();
            if (u instanceof Estudiante && ((Estudiante) u).getCodigoEstudiantil().equalsIgnoreCase(codigoUsuario)) {
                prestamosDelUsuario.add(prestamo);
            } else if (u instanceof Docente && ((Docente) u).getCodigoDocente().equalsIgnoreCase(codigoUsuario)) {
                prestamosDelUsuario.add(prestamo);
            } else if (u instanceof FuncionarioSolicitante && ((FuncionarioSolicitante) u).getCodigoFuncionario().equalsIgnoreCase(codigoUsuario)) {
                prestamosDelUsuario.add(prestamo);
            }
        }
        return prestamosDelUsuario;
    }


    public void guardarAlFinal() {
        persistencia.escribir(this.prestamos);
    }
}