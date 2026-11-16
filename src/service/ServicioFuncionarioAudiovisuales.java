package service;

import model.FuncionarioAudiovisuales;
import model.Prestamo;
import model.EstadoPrestamo;
import persistence.PersistenciaFuncionarioAudiovisuales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicioFuncionarioAudiovisuales {

    private ArrayList<FuncionarioAudiovisuales> funcionarios;
    private PersistenciaFuncionarioAudiovisuales persistencia;

    public ServicioFuncionarioAudiovisuales() {
        this.persistencia = new PersistenciaFuncionarioAudiovisuales();
        this.funcionarios = persistencia.leer();

        if (this.funcionarios == null) {
            this.funcionarios = new ArrayList<>();
        }
    }

    public void agregarFuncionario(FuncionarioAudiovisuales funcionario) {
        this.funcionarios.add(funcionario);
    }

    public FuncionarioAudiovisuales buscarPorDocumento(String documentoIdentidad) {
        for (FuncionarioAudiovisuales funcionario : funcionarios) {
            if (funcionario.getDocumentoIdentidad().equalsIgnoreCase(documentoIdentidad)) {
                return funcionario;
            }
        }
        return null;
    }

    public ArrayList<FuncionarioAudiovisuales> obtenerTodos() {
        return this.funcionarios;
    }


    public void registrarEntrega(Prestamo prestamo) {
        if (prestamo != null) {

            prestamo.setFechaEjecucion(LocalDateTime.now());

            prestamo.setEstadoPrestamo(EstadoPrestamo.ACTIVO);
        }
    }

    public void registrarRecepcion(Prestamo prestamo, String observaciones) {
        if (prestamo != null) {
            prestamo.setFechaDevolucionReal(LocalDateTime.now());
            prestamo.setObservaciones(observaciones);

            prestamo.setEstadoPrestamo(EstadoPrestamo.DEVUELTO);
        }
    }

    public boolean puedeSolicitarPrestamo(String documentoIdentidad) {
        return buscarPorDocumento(documentoIdentidad) != null;
    }

    public Map<String, String> getDatosAutocompletar(String documentoIdentidad) {
        FuncionarioAudiovisuales funcionario = buscarPorDocumento(documentoIdentidad);
        Map<String, String> datos = new HashMap<>();

        if (funcionario != null) {
            datos.put("id", funcionario.getId());
            datos.put("nombreCompleto", funcionario.getNombre());
            datos.put("documentoIdentidad", funcionario.getDocumentoIdentidad());
        }

        return datos;
    }


    public void guardarAlFinal() {
        persistencia.escribir(this.funcionarios);
    }
}