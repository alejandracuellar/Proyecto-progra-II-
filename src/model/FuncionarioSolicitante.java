package model;

import java.util.HashMap;
import java.util.Map;

public class FuncionarioSolicitante extends Usuario{

    private String codigoFuncionario;
    private String correoInstitucional;
    private String cargo;
    private String dependencia;

    public FuncionarioSolicitante() { super(); }

    public FuncionarioSolicitante(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependecia, String firmaDigital, String codigoFuncionario, String correoInstitucional, String cargo, String dependencia) {
        super(id, documentoIdentidad, nombreCompleto, telefono, escuelaDependecia, firmaDigital);
        this.codigoFuncionario = codigoFuncionario;
        this.correoInstitucional = correoInstitucional;
        this.cargo = cargo;
        this.dependencia = dependencia;
    }

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    @Override
    public Map<String, String> getDatosAutocompletar() {
        Map<String, String> datos = new HashMap<>();
        datos.put("nombreCompleto", getNombreCompleto());
        datos.put("documentoIdentidad", getDocumentoIdentidad());
        datos.put("telefono", getTelefono());
        datos.put("escuelaDependencia", getEscuelaDependencia());
        datos.put("firmaDigital", getFirmaDigital());
        datos.put("codigoFuncionario", this.codigoFuncionario);
        datos.put("cargo", this.cargo);
        return datos;
    }

    @Override
    public boolean puedeSolicitarPrestamo() {
        return true;
    }
}
