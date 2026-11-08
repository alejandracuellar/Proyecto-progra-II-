package model;

import java.util.HashMap;
import java.util.Map;

public class Docente extends Usuario{

    private String codigoDocente;
    private String correoInstitucional;

    public Docente(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependecia, String firmaDigital, String codigoDocente, String correoInstitucional) {
        super(id, documentoIdentidad, nombreCompleto, telefono, escuelaDependecia, firmaDigital);
        this.codigoDocente = codigoDocente;
        this.correoInstitucional = correoInstitucional;
    }

    public String getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(String codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    @Override
    public Map<String, String> getDatosAutocompletar() {
        Map<String, String> datos = new HashMap<>();
        datos.put("nombre completo", getNombreCompleto());
        datos.put("documento de identidad", getDocumentoIdentidad());
        datos.put("telefono", getTelefono());
        datos.put("escuela o dependencia", getEscuelaDependencia());
        datos.put("firma digital", getFirmaDigital());
        datos.put("codigo docente", this.codigoDocente);
        datos.put("correo institucional", this.correoInstitucional);
        return datos;
    }

    @Override
    public boolean puedeSolicitarPrestamo() {
        return true;
    }
}
