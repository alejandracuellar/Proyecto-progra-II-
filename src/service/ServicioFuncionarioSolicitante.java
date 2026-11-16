package service;

import model.EstadoPrestamo;
import model.FuncionarioSolicitante;
import model.Prestamo;
import persistence.PersistenciaFuncionarioSolicitante;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioFuncionarioSolicitante {

    private ArrayList<FuncionarioSolicitante> funcionarios;
    private PersistenciaFuncionarioSolicitante persistencia;

    public ServicioFuncionarioSolicitante() {
        this.persistencia = new PersistenciaFuncionarioSolicitante();
        this.funcionarios = persistencia.leer();

        if (this.funcionarios == null) {
            this.funcionarios = new ArrayList<>();
        }
    }


    public void agregarFuncionario(FuncionarioSolicitante funcionario) {
        this.funcionarios.add(funcionario);
    }

    public FuncionarioSolicitante buscarPorCodigo(String codigoFuncionario) {
        for (FuncionarioSolicitante funcionario : funcionarios) {
            if (funcionario.getCodigoFuncionario().equalsIgnoreCase(codigoFuncionario)) {
                return funcionario;
            }
        }
        return null;
    }

    public FuncionarioSolicitante buscarPorDocumento(String documentoIdentidad) {
        for (FuncionarioSolicitante funcionario : funcionarios) {
            if (funcionario.getDocumentoIdentidad().equalsIgnoreCase(documentoIdentidad)) {
                return funcionario;
            }
        }
        return null;
    }

    public ArrayList<FuncionarioSolicitante> obtenerTodos() {
        return this.funcionarios;
    }

    public boolean puedeSolicitarPrestamo(String codigoFuncionario, LocalDateTime fechaInicioNueva, LocalDateTime fechaFinNueva, List<Prestamo> prestamosDelFuncionario) {
        FuncionarioSolicitante funcionario = buscarPorCodigo(codigoFuncionario);

        if (funcionario == null) {
            return false;
        }

        if (prestamosDelFuncionario != null) {
            for (Prestamo prestamo : prestamosDelFuncionario) {

                boolean prestamoVigente = prestamo.getEstadoPrestamo() != EstadoPrestamo.DEVUELTO &&
                        prestamo.getEstadoPrestamo() != EstadoPrestamo.CANCELADO;

                if (prestamoVigente) {
                    LocalDateTime inicioExistente = prestamo.getFechaEjecucion();
                    LocalDateTime finExistente = prestamo.getFechaDevolucionEsperada();

                    boolean horarioPermitido = fechaInicioNueva.isBefore(finExistente) && fechaFinNueva.isAfter(inicioExistente);

                    if (horarioPermitido) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Map<String, String> getDatosAutocompletar(String codigoFuncionario) {
        FuncionarioSolicitante funcionario = buscarPorCodigo(codigoFuncionario);
        Map<String, String> datos = new HashMap<>();

        if (funcionario != null) {

            datos.put("nombreCompleto", funcionario.getNombreCompleto());
            datos.put("documentoIdentidad", funcionario.getDocumentoIdentidad());
            datos.put("telefono", funcionario.getTelefono());

            datos.put("codigoFuncionario", funcionario.getCodigoFuncionario());
            datos.put("correoInstitucional", funcionario.getCorreoInstitucional());
            datos.put("cargo", funcionario.getCargo());
            datos.put("dependencia", funcionario.getDependencia());
        }

        return datos;
    }


    public void guardarAlFinal() {
        persistencia.escribir(this.funcionarios);
    }
}